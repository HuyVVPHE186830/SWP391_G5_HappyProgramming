package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import model.Course;
import model.Category;
import model.Course_Category;
import model.User;

public class CourseDAO extends DBContext {

    public static void main(String[] args) {
        CourseDAO dao = new CourseDAO();
       int count = dao.findTotalRecordOrderByNumberOfMentee();
        List<Course> list = dao.findCourseOrderByNumberOfMentee(1)     ;
        for (Course course : list) {
            System.out.println(course);
        }
        System.out.println(count);
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

    public List<Course> getSameCourse(int categoryId, int courseId) {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT Course.courseId, Course.courseName, Course.courseDescription, Course.createdAt\n"
                + "FROM Course \n"
                + "JOIN Course_Category ON Course.courseId = Course_Category.courseId\n"
                + "JOIN Category ON Category.categoryId = Course_Category.categoryId\n"
                + "WHERE Category.categoryId = ?\n"
                + "AND Course.courseId != ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, categoryId);
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
                + "    SELECT Course.*, \n"
                + "           ROW_NUMBER() OVER (PARTITION BY Category.categoryId ORDER BY Course.courseId) AS rn\n"
                + "    FROM Course \n"
                + "    JOIN Course_Category ON Course.courseId = Course_Category.courseId\n"
                + "    JOIN Category ON Category.categoryId = Course_Category.categoryId\n"
                + ")\n"
                + "SELECT courseId, courseName, courseDescription, createdAt\n"
                + "FROM RankedCourses\n"
                + "WHERE rn <= 2;";
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

    public List<Course> getOtherCourseHasOtherCategory(int categoryId) {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT Course.courseId, Course.courseName, Course.courseDescription, Course.createdAt\n"
                + "FROM Course \n"
                + "JOIN Course_Category ON Course.courseId = Course_Category.courseId\n"
                + "JOIN Category ON Category.categoryId = Course_Category.categoryId\n"
                + "WHERE Category.categoryId != ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, categoryId);
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

        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM [Course] WHERE courseName LIKE ? \n"
                + "                 ORDER BY CourseId \n"
                + "                 OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + keyword + "%");
            int recordsPerPage = 5;
            int offset = (page - 1) * recordsPerPage;
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, recordsPerPage);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                
                Course course = new Course();
                course.setCourseId(resultSet.getInt("CourseId"));
                course.setCourseName(resultSet.getString("CourseName"));
                course.setCourseDescription(resultSet.getString("courseDescription"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return courses;

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
        String sql = "SELECT *\n"
                + "FROM Course c\n"
                + "JOIN Participate p ON c.courseId = p.courseId\n"
                + "JOIN [User] u ON p.username = u.username\n"
                + "JOIN Role r ON u.roleId = r.roleId\n"
                + "WHERE r.roleId = 2 AND u.username = ?;";

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

    public List<Course> getAllCourse2(int page) {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM [Course] "
                + "ORDER BY courseId "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            int recordsPerPage = 5;
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
            int recordsPerPage = 5;
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
                Course e = new Course(id, name, des, date);
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
        int totalRecord = 5;
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
            int recordsPerPage = 5;
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
            int recordsPerPage = 5;
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
                + "WHERE p.userName = ? AND p.participateRole = 2 AND c.courseName LIKE ? "
                + "ORDER BY c.courseName OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setString(2, "%" + keyword + "%");
            ps.setInt(3, (page - 1) * 5);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                courses.add(mapCourse(rs));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public int countMentoringCoursesByName(String userName, String keyword) {
        String sql = "SELECT COUNT(*) FROM Course c "
                + "INNER JOIN Participate p ON c.courseId = p.courseId "
                + "WHERE p.userName = ? AND p.participateRole = 2 AND c.courseName LIKE ?";

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
                + "WHERE p.userName = ? AND p.participateRole = 2 "
                + "ORDER BY c.courseName OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setInt(2, (page - 1) * 5);
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
                + "WHERE p.userName = ? AND p.participateRole = 2";

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
                + "WHERE p.userName = ? AND p.participateRole = 3 AND c.courseName LIKE ? "
                + "ORDER BY c.courseName OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setString(2, "%" + keyword + "%");
            ps.setInt(3, (page - 1) * 5);  // Pagination logic
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
                + "WHERE p.userName = ? AND p.participateRole = 3 AND c.courseName LIKE ?";

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
                + "WHERE p.userName = ? AND p.participateRole = 3 "
                + "ORDER BY c.courseName OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setInt(2, (page - 1) * 5);
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
                + "WHERE p.userName = ? AND p.participateRole = 3";

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
                + "WHERE p.userName = ? AND p.participateRole = 2 AND cc.categoryId = ? "
                + "ORDER BY c.courseName OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setString(2, categoryId);
            ps.setInt(3, (page - 1) * 5);
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
                + "WHERE p.userName = ? AND p.participateRole = 2 AND cc.categoryId = ?";

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
                + "WHERE p.userName = ? AND p.participateRole = 3 AND cc.categoryId = ? "
                + "ORDER BY c.courseName OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setString(2, categoryId);
            ps.setInt(3, (page - 1) * 5);
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
                + "WHERE p.userName = ? AND p.participateRole = 3 AND cc.categoryId = ?";

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
}
