package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Blog;
import model.Tag;  // Import Tag class

public class BlogDAO extends DBContext {

    // Method to get all blogs created by a specific user
    public List<Blog> getAllBlogs(String userName) {
        List<Blog> list = new ArrayList<>();
        String sql = "SELECT b.blog_Id, b.title, b.content, b.user_Name, bi.image_url, t.tag_id, t.tag_name " +
                     "FROM Blogs b " +
                     "LEFT JOIN blog_images bi ON b.blog_Id = bi.blog_id " +
                     "LEFT JOIN blog_tags bt ON b.blog_Id = bt.blog_id " +
                     "LEFT JOIN tags t ON bt.tag_id = t.tag_id " +
                     "WHERE b.user_Name = ? ORDER BY b.created_At DESC";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, userName); // Bind the userName parameter
            ResultSet rs = st.executeQuery();

            // Temporary storage for blog data
            int currentBlogId = -1;
            Blog currentBlog = null;
            List<String> imageUrls = new ArrayList<>();
            List<Tag> tags = new ArrayList<>();

            while (rs.next()) {
                int blogId = rs.getInt("blog_Id");

                if (blogId != currentBlogId) {
                    if (currentBlog != null) {
                        currentBlog.setTags(tags);
                        currentBlog.setImageUrls(imageUrls);
                        list.add(currentBlog);
                    }

                    // Create a new Blog object for a new blogId
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    String createdBy = rs.getString("user_Name");

                    currentBlog = new Blog(blogId, title, content, createdBy, new ArrayList<>(), new ArrayList<>());
                    currentBlogId = blogId;

                    // Reset imageUrls and tags lists for the new blog
                    imageUrls = new ArrayList<>();
                    tags = new ArrayList<>();
                }

                // Add the current row's imageUrl and tag to the respective lists
                String imageUrl = rs.getString("image_url");
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    imageUrls.add(imageUrl);
                }

                // Create a new Tag object and add to the tags list
                int tagId = rs.getInt("tag_id");
                String tagName = rs.getString("tag_name");
                if (tagName != null && !tagName.isEmpty()) {
                    Tag tag = new Tag(tagId, tagName); // Create Tag object
                    tags.add(tag); // Add Tag to list
                }
            }

            // Add the last blog in the result set
            if (currentBlog != null) {
                currentBlog.setTags(tags);
                currentBlog.setImageUrls(imageUrls);
                list.add(currentBlog);
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return list; // Return the list of blogs
    }

    // Main method to test the getAllBlogs method
    public static void main(String[] args) {
        BlogDAO dao = new BlogDAO();
        List<Blog> blogs = dao.getAllBlogs("antt");
        for (Blog blog : blogs) {
            System.out.println(blog.toString());
        }
    }
}
