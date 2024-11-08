package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import model.Course;
import model.Category;
import model.Course_Category;
import model.User;

public class CourseDAO extends DBContext {

    public static void main(String[] args) {
        CourseDAO dao = new CourseDAO();
        CourseCategoryDAO daoCC = new CourseCategoryDAO();
//        int count = dao.findTotalRecordOrderByNumberOfMentee();
////        List<Integer> in = new ArrayList<>();
////        in.add(1);
////        in.add(3);
////        List<Integer> sameCategoryId = daoCC.getCategoryIdByCourseId(5);
//        List<Course> list = dao.getAll();
//        List<Course> otherlist = dao.getOtherCourses(list);
//        for (Course course : list) {
//            System.out.println(course);
//        }
////        List<String> string = dao.getMenteeByCourse(1, 1);
////        for (String string1 : string) {
////            System.out.println(string1);
////        }
////        List<Course> list = dao.getAllCoursesByUsernameOfMentor("anmentor");
////        List<Course> otherlist = dao.getOtherCourses(list);
////        for (Course course : otherlist) {
////            System.out.println(course);
////        }
//        List<String> string = dao.getUserByCourse(1, 1, "huyenmentor");
//        for (String string1 : string) {
//            System.out.println(string1);
//        }
//        int num = dao.getTotalParticipants(1, 1, "huyenmentor");
//        System.out.println(num);
////        int totalRecord = dao.findTotalRecordEachCategoryLessThan2Courses();
////        System.out.println(totalRecord);
        dao.setMenteeStatus(1, "minhnd", 0, "phuongmentor");
    }

    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM Category";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("categoryId");
                String name = rs.getString("categoryName");
                Category c = new Category(id, name);
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public List<Course> getAllCoursesExceptOne(int excludedCourseId) {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM Course WHERE courseId != ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, excludedCourseId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("courseId");
                String name = rs.getString("courseName");
                String description = rs.getString("courseDescription");
                Course c = new Course(id, name, description);
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public List<Course> getAllCoursesOfMentorExceptOne(int excludedCourseId, String username) {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM Course join Participate on Course.courseId = Participate.courseId\n"
                + "join [User] on Participate.username = [User].username\n"
                + "WHERE Course.courseId != ? and [User].username = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, excludedCourseId);
            st.setString(2, username);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("courseId");
                String name = rs.getString("courseName");
                String description = rs.getString("courseDescription");
                Course c = new Course(id, name, description);
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public List<Course> getAllCoursesByUsernameOfMentor(String username) {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM Course join Participate on Course.courseId = Participate.courseId\n"
                + "join [User] on Participate.username = [User].username\n"
                + "WHERE [User].username = ? and Participate.ParticipateRole = 2";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("courseId");
                String name = rs.getString("courseName");
                String description = rs.getString("courseDescription");
                Course c = new Course(id, name, description);
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public List<Course> getOtherCourses(List<Course> courses) {
        List<Course> list = new ArrayList<>();

        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < courses.size(); i++) {
            placeholders.append("?");
            if (i < courses.size() - 1) {
                placeholders.append(", ");
            }
        }

        String sql = "SELECT courseId, courseName, courseDescription FROM Course "
                + (courses.size() > 0 ? "WHERE courseId NOT IN (" + placeholders.toString() + ")" : "");
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            // Đặt giá trị courseId cho các placeholder
            for (int i = 0; i < courses.size(); i++) {
                st.setInt(i + 1, courses.get(i).getCourseId());
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("courseId");
                String name = rs.getString("courseName");
                String description = rs.getString("courseDescription");
                Course c = new Course(id, name, description);
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public int countCourse() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total FROM Course";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return count;
    }

    public List<Course> getAllCourse() {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM [Course]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("courseId");
                String name = rs.getString("courseName");
                String des = rs.getString("courseDescription");
                String dat = rs.getString("createdAt");
                Date date = rs.getDate("createdAt");
                Course e = new Course(id, name, des, date);

                list.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public List<Course> getAllCourseOrderByDESCMenteeNum() {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT Course.courseId, Course.courseName, CAST(Course.courseDescription AS NVARCHAR(MAX)) AS courseDescription, Course.createdAt\n"
                + "FROM Course \n"
                + "JOIN Participate ON Course.courseId = Participate.courseId\n"
                + "JOIN [User] ON [User].username = Participate.username\n"
                + "WHERE [User].roleId = 3\n"
                + "GROUP BY Course.courseId, Course.courseName, CAST(Course.courseDescription AS NVARCHAR(MAX)), Course.createdAt\n"
                + "ORDER BY COUNT(Course.courseId) DESC;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("courseId");
                String name = rs.getString("courseName");
                String des = rs.getString("courseDescription");
                Date date = rs.getDate("createdAt");
                Course e = new Course(id, name, des, date);

                list.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public List<Course> getSameCourse(int courseId) {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT Course.courseId, Course.courseName, Course.courseDescription, Course.createdAt "
                + "FROM Course "
                + "JOIN Course_Category ON Course.courseId = Course_Category.courseId "
                + "JOIN Category ON Category.categoryId = Course_Category.categoryId "
                + "WHERE Course.courseId != ? "
                + "AND Category.categoryId IN (SELECT categoryId FROM Course_Category WHERE courseId = ?)";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, courseId);
            st.setInt(2, courseId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("courseId");
                String name = rs.getString("courseName");
                String des = rs.getString("courseDescription");
                Date date = rs.getDate("createdAt");
                Course e = new Course(id, name, des, date);
                list.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public List<Course> getEachCategoryLessThan2Courses() {
        List<Course> list = new ArrayList<>();
        String sql = "WITH RankedCourses AS (\n"
                + "    SELECT Course.courseId, Course.courseName, \n"
                + "           CAST(Course.courseDescription AS VARCHAR(255)) AS courseDescription, \n"
                + "           Course.createdAt, \n"
                + "           ROW_NUMBER() OVER (PARTITION BY Category.categoryId ORDER BY Course.courseId) AS rn\n"
                + "    FROM Course \n"
                + "    JOIN Course_Category ON Course.courseId = Course_Category.courseId\n"
                + "    JOIN Category ON Category.categoryId = Course_Category.categoryId\n"
                + "), DistinctCourses AS (\n"
                + "    SELECT DISTINCT courseId, courseName, courseDescription, createdAt\n"
                + "    FROM RankedCourses\n"
                + "    WHERE rn <= 2\n"
                + ")\n"
                + "SELECT * FROM DistinctCourses;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("courseId");
                String name = rs.getString("courseName");
                String des = rs.getString("courseDescription");
                Date date = rs.getDate("createdAt");
                Course e = new Course(id, name, des, date);

                list.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public List<Course> getOtherCourseHasOtherCategory(List<Integer> categoryIds) {
        List<Course> list = new ArrayList<>();
        // Dynamically generate placeholders for the categoryIds
        String placeholders = categoryIds.stream().map(id -> "?").collect(Collectors.joining(","));

        String sql = "SELECT Course.courseId, Course.courseName, CAST(Course.courseDescription AS CHAR) AS courseDescription, Course.createdAt "
                + "FROM Course "
                + "JOIN Course_Category ON Course.courseId = Course_Category.courseId "
                + "JOIN Category ON Category.categoryId = Course_Category.categoryId "
                + "GROUP BY Course.courseId, Course.courseName, CAST(Course.courseDescription AS CHAR), Course.createdAt "
                + "HAVING SUM(CASE WHEN Category.categoryId IN (" + placeholders + ") THEN 1 ELSE 0 END) = 0";

        try {
            PreparedStatement st = connection.prepareStatement(sql);

            // Set the category IDs in the prepared statement
            for (int i = 0; i < categoryIds.size(); i++) {
                st.setInt(i + 1, categoryIds.get(i));
            }

            ResultSet rs = st.executeQuery();

            // Process the result set
            while (rs.next()) {
                int id = rs.getInt("courseId");
                String name = rs.getString("courseName");
                String des = rs.getString("courseDescription");
                Date date = rs.getDate("createdAt");
                Course e = new Course(id, name, des, date);

                list.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();  // Log the exception to make debugging easier
        }

        return list;
    }

    public List<Course> getOtherCourseHasOtherCategory(int categoryId) {
        // You can implement this method similarly, or it could call the previous method.
        // Assuming you want to handle a single categoryId, here's a simple version:
        List<Course> list = new ArrayList<>();
        return list; // Adjust implementation as needed based on your requirements
    }

    public List<Course_Category> getAllCategories_Course() {
        List<Course_Category> list = new ArrayList<>();
        String sql = "select * from [Course_Category]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("categoryId");
                int name = rs.getInt("courseId");
                Course_Category e = new Course_Category(id, name);
                list.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public boolean isCourseNameDuplicate(String courseName) {
        boolean isDuplicate = false;
        String sql = "SELECT courseId FROM Course WHERE courseName = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, courseName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isDuplicate = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDuplicate;
    }

    public boolean addCourse(Course c) {
        boolean f = false;
        if (isCourseNameDuplicate(c.getCourseName())) {
            return false;
        }
        try {
            String sql = "insert into Course(courseName, createdAt, courseDescription) "
                    + "values(?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, c.getCourseName());
            ps.setTimestamp(2, new java.sql.Timestamp(c.getCreatedAt().getTime()));
            ps.setString(3, c.getCourseDescription());

            int i = ps.executeUpdate();
            if (i == 1) {
                f = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    public int getCourseId(Course c) {
        int courseId = -1;
        try {
            String sql = "select courseId from Course where courseName = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, c.getCourseName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                courseId = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseId;
    }

    public boolean addCourseCategory(int categoryId, int courseId) {
        boolean f = false;
        String sql = "INSERT INTO Course_Category(categoryId, courseId) VALUES (?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, categoryId);
            ps.setInt(2, courseId);
            int i = ps.executeUpdate();
            if (i == 1) {
                f = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return f;
    }

    public List<Course> getAll() {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM [Course]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int courseId = rs.getInt("courseId");
                String courseName = rs.getString("courseName");
                String courseDescription = rs.getString("courseDescription");
                Date createdAt = rs.getDate("createdAt");
                Course c = new Course(courseId, courseName, courseDescription, createdAt);

                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public List<Course> getAllByCourseId(int courseId) {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM [Course] WHERE courseId = ?";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, courseId);
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                String courseName = rs.getString("courseName");
                String courseDescription = rs.getString("courseDescription");
                Date createdAt = rs.getDate("createdAt");
                Course c = new Course(courseId, courseName, courseDescription, createdAt);
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public Course getCourseByCourseId(int courseId) {
        Course list = new Course();
        String sql = "SELECT * FROM [Course] where courseId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, courseId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String courseName = rs.getString("courseName");
                String courseDescription = rs.getString("courseDescription");
                Date createdAt = rs.getDate("createdAt");
                Course c = new Course(courseId, courseName, courseDescription, createdAt);
                list = c;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public int findTotalRecordByName(String keyword) {
        String sql = "SELECT count(*)\n"
                + "  FROM [Course]\n"
                + "  where [name] like ?";
        int totalRecord = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + keyword + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                totalRecord = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }

        return totalRecord;
    }

    public List<Course> findByName(String keyword, int page) {

        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM [Course] WHERE courseName LIKE ? \n"
                + "                 ORDER BY CourseId \n"
                + "                 OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "%" + keyword + "%");
            int recordsPerPage = 6;
            int offset = (page - 1) * recordsPerPage;
            st.setInt(2, offset);
            st.setInt(3, recordsPerPage);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("courseId");
                String name = rs.getString("courseName");
                String des = rs.getString("courseDescription");
                Date date = rs.getDate("createdAt");
                List<Category> listC = getCategoriesForCourse(id);
                Course e = new Course(id, name, des, date, listC);
                list.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return list;

    }

    public List<User> getAllMentor() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM [User] where roleId = 2";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                Date dob = rs.getDate("dob");
                String mail = rs.getString("mail");
                Date createdDate = rs.getDate("createdDate");
                String avatarPath = rs.getString("avatarPath");
                String cvPath = rs.getString("cvPath");
                boolean activeStatus = rs.getBoolean("activeStatus");
                boolean isVerified = rs.getBoolean("isVerified");
                String verificationCode = rs.getString("verification_code");
                int roleId = rs.getInt("roleId");
                User u = new User(id, username, password, firstName, lastName, dob, mail, createdDate, avatarPath, cvPath, activeStatus, isVerified, verificationCode, roleId);
                list.add(u);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

//    public int findTotalRecordByCategory(String categoryId) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
    public List<Course> findByCategory(String categoryId) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.*\n"
                + "FROM Course c\n"
                + "JOIN Course_Category cc ON c.courseId = cc.courseId\n"
                + "WHERE cc.categoryId = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, categoryId);
            // Tính toán offset và số lượng bản ghi trên một trang (giả sử mỗi trang có 10 khóa học)
//            int recordsPerPage = 12;
//            int offset = (page - 1) * recordsPerPage;
//            preparedStatement.setInt(2, offset);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Giả sử bạn có một lớp Course với các trường phù hợp
                Course course = new Course();
                course.setCourseId(resultSet.getInt("CourseId"));
                course.setCourseName(resultSet.getString("CourseName"));
                course.setCourseDescription(resultSet.getString("courseDescription"));
                // Thêm các trường khác nếu có
                course.setCreatedAt(resultSet.getDate("createdAt"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ
        }

        return courses;

    }

    public List<Course> findByUsername2(String username) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Course c JOIN Participate p ON c.courseId = p.courseId WHERE p.mentorUsername = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            // Tính toán offset và số lượng bản ghi trên một trang (giả sử mỗi trang có 10 khóa học)
//            int recordsPerPage = 12;
//            int offset = (page - 1) * recordsPerPage;
//            preparedStatement.setInt(2, offset);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Giả sử bạn có một lớp Course với các trường phù hợp
                Course course = new Course();
                course.setCourseId(resultSet.getInt("CourseId"));
                course.setCourseName(resultSet.getString("CourseName"));
                course.setCourseDescription(resultSet.getString("courseDescription"));
                // Thêm các trường khác nếu có
                course.setCreatedAt(resultSet.getDate("createdAt"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ
        }

        return courses;
    }

    public int findTotalRecordByCategory(String categoryId) {
        String sql = "SELECT COUNT(*) AS total_records\n"
                + "FROM Course c\n"
                + "JOIN Course_Category cc ON c.courseId = cc.courseId\n"
                + "WHERE cc.categoryId = ?";
        int totalRecord = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                totalRecord = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }

        return totalRecord;
    }

    public int findTotalRecordAllCourses() {
        String sql = "SELECT count(*) FROM [Course]";
        int totalRecord = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                totalRecord = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }

        return totalRecord;
    }

    public List<Category> getCategoriesForCourse(int id) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT *\n"
                + "FROM Category cat\n"
                + "INNER JOIN Course_Category cc ON cat.categoryId = cc.categoryId\n"
                + "WHERE cc.courseId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int iD = rs.getInt("categoryId");
                String name = rs.getString("categoryName");
                categories.add(new Category(iD, name));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return categories;

    }

    public List<Course> getCourseByDate() {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM Course ORDER BY createdAt DESC;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("courseId");
                String name = rs.getString("courseName");
                String des = rs.getString("courseDescription");
                Date date = rs.getDate("createdAt");
                List<Category> listC = getCategoriesForCourse(id);
                Course e = new Course(id, name, des, date, listC);
                list.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public int getNumOfMentee(int id) {

        String sql = "SELECT COUNT(*)\n"
                + "			FROM Course c\n"
                + "			JOIN Participate p ON c.courseId = p.courseId\n"
                + "			JOIN [User] u ON p.username = u.username\n"
                + "			JOIN Status s ON p.statusId = s.statusId\n"
                + "			WHERE p.participateRole = 3 AND p.statusId = 1 AND c.courseId = ?\n"
                + "			GROUP BY c.courseId, c.courseName, \n"
                + "			CAST(c.courseDescription AS NVARCHAR(MAX)), c.createdAt";
        int totalMentee = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                totalMentee = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }

        return totalMentee;

    }

    public List<Course> getAllCourse2(int page) {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM [Course] "
                + "ORDER BY courseId "
                + "OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            int offset = (page - 1) * 6;
            st.setInt(1, offset); // Corrected: set offset first
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("courseId");
                String name = rs.getString("courseName");
                String des = rs.getString("courseDescription");
                Date date = rs.getDate("createdAt");
                List<Category> listC = getCategoriesForCourse(id);
                int countMentee = getNumOfMentee(id);
                Course e = new Course(id, name, des, date, countMentee, listC);
                list.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public int findTotalRecordEachCategoryLessThan2Courses() {
        String sql = "WITH RankedCourses AS (\n"
                + "    SELECT Course.courseId, Course.courseName, \n"
                + "           CAST(Course.courseDescription AS VARCHAR(255)) AS courseDescription, \n"
                + "           Course.createdAt, \n"
                + "           ROW_NUMBER() OVER (PARTITION BY Category.categoryId ORDER BY Course.courseId) AS rn\n"
                + "    FROM Course \n"
                + "    JOIN Course_Category ON Course.courseId = Course_Category.courseId\n"
                + "    JOIN Category ON Category.categoryId = Course_Category.categoryId\n"
                + "), DistinctCourses AS (\n"
                + "    SELECT DISTINCT courseId, courseName, courseDescription, createdAt\n"
                + "    FROM RankedCourses\n"
                + "    WHERE rn <= 2\n"
                + ")\n"
                + "SELECT count(*) FROM [DistinctCourses]";
        int totalRecord = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                totalRecord = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }

        return totalRecord;
    }

    public List<Course> getAllCourse3(int page) {
        List<Course> list = new ArrayList<>();
        String sql = "WITH RankedCourses AS (\n"
                + "    SELECT Course.courseId, Course.courseName, \n"
                + "           CAST(Course.courseDescription AS VARCHAR(255)) AS courseDescription, \n"
                + "           Course.createdAt, \n"
                + "           ROW_NUMBER() OVER (PARTITION BY Category.categoryId ORDER BY Course.courseId) AS rn\n"
                + "    FROM Course \n"
                + "    JOIN Course_Category ON Course.courseId = Course_Category.courseId\n"
                + "    JOIN Category ON Category.categoryId = Course_Category.categoryId\n"
                + "), DistinctCourses AS (\n"
                + "    SELECT DISTINCT courseId, courseName, courseDescription, createdAt\n"
                + "    FROM RankedCourses\n"
                + "    WHERE rn <= 2\n"
                + ")\n"
                + "SELECT * FROM DistinctCourses\n" // Removed the semicolon here
                + "ORDER BY courseId "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY"; // This is part of the main query

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            int recordsPerPage = 4;
            int offset = (page - 1) * recordsPerPage;
            st.setInt(1, offset); // Corrected: set offset first
            st.setInt(2, recordsPerPage);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("courseId");
                String name = rs.getString("courseName");
                String des = rs.getString("courseDescription");
                Date date = rs.getDate("createdAt");
                Course e = new Course(id, name, des, date);
                list.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public List<Course> findByCategory2(String categoryId, int page) {

        List<Course> list = new ArrayList<>();
        String sql = "SELECT c.*\n"
                + "FROM Course c\n"
                + "JOIN Course_Category cc ON c.courseId = cc.courseId\n"
                + "WHERE cc.categoryId = ? ORDER BY courseId OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            int recordsPerPage = 6;
            int offset = (page - 1) * recordsPerPage;
            st.setString(1, categoryId);
            st.setInt(2, offset);
            st.setInt(3, recordsPerPage);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("courseId");
                String name = rs.getString("courseName");
                String des = rs.getString("courseDescription");
                Date date = rs.getDate("createdAt");
                List<Category> listC = getCategoriesForCourse(id);
                Course e = new Course(id, name, des, date, listC);
                list.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;

    }

    public int findTotalRecordOrderByNumberOfMentee() {
        String sql = "SELECT COUNT(*) AS total_records\n"
                + "FROM (\n"
                + "    SELECT c.courseId, c.courseName, \n"
                + "           CAST(c.courseDescription AS NVARCHAR(MAX)) AS courseDescription, \n"
                + "           c.createdAt, \n"
                + "           COUNT(p.username) AS user_count\n"
                + "    FROM Course c\n"
                + "    JOIN Participate p ON c.courseId = p.courseId\n"
                + "    JOIN [User] u ON p.username = u.username\n"
                + "    JOIN Status s ON p.statusId = s.statusId\n"
                + "    WHERE p.participateRole = 3 AND p.statusId = 1\n"
                + "    GROUP BY c.courseId, c.courseName, \n"
                + "             CAST(c.courseDescription AS NVARCHAR(MAX)), \n"
                + "             c.createdAt\n"
                + ") AS subquery";
        int totalRecord = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                totalRecord = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }

        return totalRecord;

    }

    public List<Course> findCourseOrderByNumberOfMentee(int page) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.courseId, c.courseName, \n"
                + "       CAST(c.courseDescription AS NVARCHAR(MAX)) AS courseDescription, \n"
                + "       c.createdAt, \n"
                + "       COUNT(p.username) AS user_count\n"
                + "FROM Course c\n"
                + "JOIN Participate p ON c.courseId = p.courseId\n"
                + "JOIN [User] u ON p.username = u.username\n"
                + "JOIN Status s ON p.statusId = s.statusId\n"
                + "WHERE p.participateRole = 3 AND p.statusId = 1\n"
                + "GROUP BY c.courseId, c.courseName, \n"
                + "         CAST(c.courseDescription AS NVARCHAR(MAX)), \n"
                + "         c.createdAt\n"
                + "ORDER BY user_count DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Tính toán offset và số lượng bản ghi trên một trang (giả sử mỗi trang có 10 khóa học)
            int recordsPerPage = 6;
            int offset = (page - 1) * recordsPerPage;
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, recordsPerPage);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Giả sử bạn có một lớp Course với các trường phù hợp
                Course course = new Course();
                course.setCourseId(resultSet.getInt("CourseId"));
                course.setCourseName(resultSet.getString("CourseName"));
                course.setCourseDescription(resultSet.getString("courseDescription"));
                // Thêm các trường khác nếu có
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ
        }

        return courses;
    }

    public List<Course> findCourseOrderByNumberOfMentee3(int page) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.courseId, c.courseName, \n"
                + "       CAST(c.courseDescription AS NVARCHAR(MAX)) AS courseDescription, \n"
                + "       c.createdAt, \n"
                + "       COUNT(p.username) AS user_count\n"
                + "FROM Course c\n"
                + "JOIN Participate p ON c.courseId = p.courseId\n"
                + "JOIN [User] u ON p.username = u.username\n"
                + "JOIN Status s ON p.statusId = s.statusId\n"
                + "WHERE p.participateRole = 3 AND p.statusId = 1\n"
                + "GROUP BY c.courseId, c.courseName, \n"
                + "         CAST(c.courseDescription AS NVARCHAR(MAX)), \n"
                + "         c.createdAt\n"
                + "ORDER BY user_count ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Tính toán offset và số lượng bản ghi trên một trang (giả sử mỗi trang có 10 khóa học)
            int recordsPerPage = 6;
            int offset = (page - 1) * recordsPerPage;
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, recordsPerPage);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Giả sử bạn có một lớp Course với các trường phù hợp
                Course course = new Course();
                course.setCourseId(resultSet.getInt("CourseId"));
                course.setCourseName(resultSet.getString("CourseName"));
                course.setCourseDescription(resultSet.getString("courseDescription"));
                // Thêm các trường khác nếu có
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ
        }

        return courses;
    }

    //My Courses
    public List<Course> searchMentoringCoursesByName(String userName, String keyword, int page) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Course c "
                + "INNER JOIN Participate p ON c.courseId = p.courseId "
                + "WHERE p.userName = ? AND p.participateRole = 2 AND p.statusId = 1 AND c.courseName LIKE ? "
                + "ORDER BY c.courseName OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setString(2, "%" + keyword + "%");
            ps.setInt(3, (page - 1) * 6);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                courses.add(mapCourse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public int countMentoringCoursesByName(String userName, String keyword) {
        String sql = "SELECT COUNT(*) FROM Course c "
                + "INNER JOIN Participate p ON c.courseId = p.courseId "
                + "WHERE p.userName = ? AND p.participateRole = 2 AND p.statusId = 1 AND c.courseName LIKE ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int totalCourses = rs.getInt(1);
                return calculateTotalPages(totalCourses);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public List<Course> getMentoringCoursesByUser(String userName, int page) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Course c "
                + "INNER JOIN Participate p ON c.courseId = p.courseId "
                + "WHERE p.userName = ? AND p.participateRole = 2 AND p.statusId = 1 "
                + "ORDER BY c.courseName OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setInt(2, (page - 1) * 6);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                courses.add(mapCourse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public int countMentoringCoursesByUser(String userName) {
        String sql = "SELECT COUNT(*) FROM Course c "
                + "INNER JOIN Participate p ON c.courseId = p.courseId "
                + "WHERE p.userName = ? AND p.participateRole = 2 AND p.statusId = 1";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int totalCourses = rs.getInt(1);
                return calculateTotalPages(totalCourses);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public List<Course> searchStudyingCoursesByName(String userName, String keyword, int page) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Course c "
                + "INNER JOIN Participate p ON c.courseId = p.courseId "
                + "WHERE p.userName = ? AND p.participateRole = 3 AND p.statusId = 1 AND c.courseName LIKE ? "
                + "ORDER BY c.courseName OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setString(2, "%" + keyword + "%");
            ps.setInt(3, (page - 1) * 6);  // Pagination logic
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                courses.add(mapCourse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public int countStudyingCoursesByName(String userName, String keyword) {
        String sql = "SELECT COUNT(*) FROM Course c "
                + "INNER JOIN Participate p ON c.courseId = p.courseId "
                + "WHERE p.userName = ? AND p.participateRole = 3 AND p.statusId = 1 AND c.courseName LIKE ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int totalCourses = rs.getInt(1);
                return calculateTotalPages(totalCourses);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public List<Course> getStudyingCoursesByUser(String userName, int page) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Course c "
                + "INNER JOIN Participate p ON c.courseId = p.courseId "
                + "WHERE p.userName = ? AND p.participateRole = 3 AND p.statusId = 1 "
                + "ORDER BY c.courseName OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setInt(2, (page - 1) * 6);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                courses.add(mapCourse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public int countStudyingCoursesByUser(String userName) {
        String sql = "SELECT COUNT(*) FROM Course c "
                + "INNER JOIN Participate p ON c.courseId = p.courseId "
                + "WHERE p.userName = ? AND p.participateRole = 3 AND p.statusId = 1";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int totalCourses = rs.getInt(1);
                return calculateTotalPages(totalCourses);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    private int calculateTotalPages(int totalCourses) {
        int itemsPerPage = 5;
        return (int) Math.ceil((double) totalCourses / itemsPerPage);
    }

    private Course mapCourse(ResultSet rs) throws SQLException {
        Course course = new Course();
        course.setCourseId(rs.getInt("courseId"));
        course.setCourseName(rs.getString("courseName"));
        course.setCourseDescription(rs.getString("courseDescription"));
        course.setCreatedAt(rs.getDate("createdAt"));
        course.setMentorName(rs.getString("mentorUsername"));
        List<Category> categories = getCategoriesByCourseId(rs.getInt("courseId"));
        course.setCategories(categories);
        return course;
    }

    public List<Category> getCategoriesByCourseId(int courseId) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT c.categoryId, c.categoryName FROM Category c "
                + "JOIN Course_Category cc ON c.categoryId = cc.categoryId "
                + "WHERE cc.courseId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("categoryId"));
                category.setCategoryName(rs.getString("categoryName"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public List<Course> getMentoringCoursesByCategory(String userName, String categoryId, int page) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Course c "
                + "INNER JOIN Participate p ON c.courseId = p.courseId "
                + "INNER JOIN Course_Category cc ON c.courseId = cc.courseId "
                + "WHERE p.userName = ? AND p.participateRole = 2 AND p.statusId = 1 AND cc.categoryId = ? "
                + "ORDER BY c.courseName OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setString(2, categoryId);
            ps.setInt(3, (page - 1) * 6);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                courses.add(mapCourse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public int countMentoringCoursesByCategory(String userName, String categoryId) {
        String sql = "SELECT COUNT(*) FROM Course c "
                + "INNER JOIN Participate p ON c.courseId = p.courseId "
                + "INNER JOIN Course_Category cc ON c.courseId = cc.courseId "
                + "WHERE p.userName = ? AND p.participateRole = 2 AND p.statusId = 1 AND cc.categoryId = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setString(2, categoryId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int totalCourses = rs.getInt(1);
                return calculateTotalPages(totalCourses);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public List<Course> getStudyingCoursesByCategory(String userName, String categoryId, int page) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Course c "
                + "INNER JOIN Participate p ON c.courseId = p.courseId "
                + "INNER JOIN Course_Category cc ON c.courseId = cc.courseId "
                + "WHERE p.userName = ? AND p.participateRole = 3 AND p.statusId = 1 AND cc.categoryId = ? "
                + "ORDER BY c.courseName OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setString(2, categoryId);
            ps.setInt(3, (page - 1) * 6);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                courses.add(mapCourse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public int countStudyingCoursesByCategory(String userName, String categoryId) {
        String sql = "SELECT COUNT(*) FROM Course c "
                + "INNER JOIN Participate p ON c.courseId = p.courseId "
                + "INNER JOIN Course_Category cc ON c.courseId = cc.courseId "
                + "WHERE p.userName = ? AND p.participateRole = 3 AND p.statusId = 1 AND cc.categoryId = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setString(2, categoryId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int totalCourses = rs.getInt(1);
                return calculateTotalPages(totalCourses);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }
    //End My Courses

    public List<Course> getAllCoursesForAdmin() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Course";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int courseId = rs.getInt("courseId");
                String courseName = rs.getString("courseName");
                String courseDescription = rs.getString("courseDescription");
                Date createdAt = rs.getDate("createdAt");
                List<Category> categories = getCategoriesByCourseId(rs.getInt("courseId"));
                int countMentee = getNumOfMentee(courseId);
                int countMentor = getNumOfMentor(courseId);
                courses.add(new Course(courseId, courseName, courseDescription, createdAt, countMentee, countMentor, categories));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public int getNumOfMentor(int id) {
        String sql = "SELECT COUNT(*) "
                + "FROM Course c "
                + "JOIN Participate p ON c.courseId = p.courseId "
                + "JOIN [User] u ON p.username = u.username "
                + "JOIN Status s ON p.statusId = s.statusId "
                + "WHERE p.participateRole = 2 AND p.statusId = 1 AND c.courseId = ? "
                + "GROUP BY c.courseId, c.courseName, "
                + "CAST(c.courseDescription AS NVARCHAR(MAX)), c.createdAt";
        int totalMentor = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                totalMentor = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalMentor;
    }

    public List<Course> getAllSearchCoursesForAdmin(String key) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Course where courseName like ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + key + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int courseId = rs.getInt("courseId");
                String courseName = rs.getString("courseName");
                String courseDescription = rs.getString("courseDescription");
                Date createdAt = rs.getDate("createdAt");
                List<Category> categories = getCategoriesByCourseId(rs.getInt("courseId"));
                int countMentee = getNumOfMentee(courseId);
                int countMentor = getNumOfMentor(courseId);
                courses.add(new Course(courseId, courseName, courseDescription, createdAt, countMentee, countMentor, categories));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public int getTotalParticipants(int courseId, int status, String mentorName) {
        int totalParticipants = 0;
        String sql = "SELECT COUNT(*) AS TotalParticipants "
                + "FROM [Participate] "
                + "WHERE participateRole = ? "
                + "AND statusId = ? "
                + "AND courseId = ? "
                + "AND mentorUsername = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Set parameters
            preparedStatement.setInt(1, 3);
            preparedStatement.setInt(2, status);
            preparedStatement.setInt(3, courseId);
            preparedStatement.setString(4, mentorName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    totalParticipants = resultSet.getInt("TotalParticipants");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalParticipants;
    }

    public boolean updateCourse(Course c) {
        boolean f = false;
        if (isCourseNameDuplicate(c.getCourseId(), c.getCourseName())) {
            return false;
        }
        String sql = "update Course set courseName = ?, courseDescription = ?"
                + " where courseId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, c.getCourseName());
            ps.setString(2, c.getCourseDescription());
            ps.setInt(3, c.getCourseId());

            int i = ps.executeUpdate();
            if (i == 1) {
                f = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    public boolean isCourseNameDuplicate(int courseId, String courseName) {
        boolean isDuplicate = false;
        String sql = "SELECT courseId FROM Course WHERE courseName = ? and courseId <> ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, courseName);
            ps.setInt(2, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isDuplicate = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDuplicate;
    }

    public boolean deleteCourseCategory(int courseId) {
        boolean f = false;
        String sql = "DELETE FROM Course_Category WHERE courseId = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, courseId);
            int i = ps.executeUpdate();
            if (i == 1) {
                f = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return f;
    }

    public List<String> getMenteeByCourse(int courseId, int status, String mentorName) {
        List<String> usernames = new ArrayList<>();

        String sql = "SELECT username "
                + "FROM [Participate] "
                + "WHERE courseId = ? "
                + "AND participateRole = ? "
                + "AND statusId = ? "
                + "AND mentorUsername = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, courseId); // courseId
            preparedStatement.setInt(2, 3); // participateRole
            preparedStatement.setInt(3, status); // statusId
            preparedStatement.setString(4, mentorName); // statusId

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    usernames.add(resultSet.getString("username"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usernames;
    }

    public List<String> getUserByCourse(int courseId, int status, String mentorName) {
        List<String> usernames = new ArrayList<>();

        String sql = "SELECT username "
                + "FROM [Participate] "
                + "WHERE courseId = ? "
                + "AND statusId = ? "
                + "AND mentorUsername = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, courseId); // courseId
            preparedStatement.setInt(2, status);
            preparedStatement.setString(3, mentorName);// statusId

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    usernames.add(resultSet.getString("username"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usernames;
    }

    public void banMentee(int courseId, String username, int status) {
        String sql = "UPDATE Participate SET statusId = ? WHERE courseId = ? AND username = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, status);     // Trạng thái muốn cập nhật, ví dụ: -1
            statement.setInt(2, courseId);   // ID của khóa học
            statement.setString(3, username); // Tên tài khoản của mentee

            // Thực thi câu lệnh update
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getMentorByCourse(int courseId, int status) {
        List<String> usernames = new ArrayList<>();

        String sql = "SELECT username "
                + "FROM [Participate] "
                + "WHERE courseId = ? "
                + "AND participateRole = ? "
                + "AND statusId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, courseId);
            preparedStatement.setInt(2, 2);
            preparedStatement.setInt(3, status);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    usernames.add(resultSet.getString("username"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usernames;
    }

    public void setMenteeStatus(int courseId, String username, int status, String mentorName) {
        String checkSql = "SELECT COUNT(*) FROM Participate WHERE courseId = ? AND username = ? AND mentorUsername = ?";
        String updateSql = "UPDATE Participate SET statusId = ? WHERE courseId = ? AND username = ? AND mentorUsername = ?";
        String insertSql = "INSERT INTO Participate (courseId, username, participateRole, statusId, mentorUsername) VALUES (?, ?, ?, ?, ?)";

        try {
            try (PreparedStatement checkStatement = connection.prepareStatement(checkSql)) {
                checkStatement.setInt(1, courseId);
                checkStatement.setString(2, username);
                checkStatement.setString(3, mentorName);

                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                            updateStatement.setInt(1, status);
                            updateStatement.setInt(2, courseId);
                            updateStatement.setString(3, username);
                            updateStatement.setString(4, mentorName);
                            updateStatement.executeUpdate();
                        }
                    } else {
                        try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                            insertStatement.setInt(1, courseId);
                            insertStatement.setString(2, username);
                            insertStatement.setInt(3, 3);
                            insertStatement.setInt(4, status);
                            insertStatement.setString(5, mentorName);
                            insertStatement.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
