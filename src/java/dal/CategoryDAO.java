/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.Category;
import model.CourseCategory;

/**
 *
 * @author Admin
 */
public class CategoryDAO extends DBContext {

    public List<Category> getAll() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM [Category]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int categoryId = rs.getInt("categoryId");
                String categoryName = rs.getString("categoryName");
                Category c = new Category(categoryId, categoryName);
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public List<Category> getAllExceptOne(List<Integer> categoryIds) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM [Category] WHERE categoryId NOT IN ("
                + categoryIds.stream().map(id -> "?").collect(Collectors.joining(",")) + ")";
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            for (int i = 0; i < categoryIds.size(); i++) {
                st.setInt(i + 1, categoryIds.get(i));
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("categoryId");
                String categoryName = rs.getString("categoryName");
                Category c = new Category(id, categoryName);
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public Category getCategoryByCategoryId(int categoryId) {
        Category c = new Category();
        String sql = "SELECT * FROM [Category] where categoryId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, categoryId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String categoryName = rs.getString("categoryName");
                c = new Category(categoryId, categoryName);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return c;
    }

    public Category getCategoryByCourseId(int courseId) {
        Category c = new Category();
        String sql = "select Category.* from Category join Course_Category on Category.categoryId = Course_Category.categoryId\n"
                + "join Course on Course.courseId = Course_Category.courseId\n"
                + "where Course.courseId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, courseId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int categoryId = rs.getInt("categoryId");
                String categoryName = rs.getString("categoryName");
                c = new Category(categoryId, categoryName);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return c;
    }

    public List<Category> getAllCategoryByCourseId(int courseId) {
        List<Category> c = new ArrayList<>();
        String sql = "select Category.* from Category join Course_Category on Category.categoryId = Course_Category.categoryId\n"
                + "join Course on Course.courseId = Course_Category.courseId\n"
                + "where Course.courseId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, courseId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int categoryId = rs.getInt("categoryId");
                String categoryName = rs.getString("categoryName");
                c.add(new Category(categoryId, categoryName));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return c;
    }

    public static void main(String[] args) {
        CategoryDAO dao = new CategoryDAO();
        List<Integer> list = new ArrayList<>();
//        List<Category> category = dao.getAllExceptOne(list);
        Category ca = dao.getCategoryByCourseId(15);
        System.out.println(ca);
//        for (Category c : category) {
//            System.out.println(c);
//        }
    }
}
