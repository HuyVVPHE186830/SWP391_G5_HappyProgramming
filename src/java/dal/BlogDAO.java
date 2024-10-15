package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.Blog;
import model.Tag;

public class BlogDAO extends DBContext {

    // Method to get all blogs
    public List<Blog> getAllBlogs() {
        List<Blog> list = new ArrayList<>();
        String sql = "SELECT b.blog_Id, b.title, b.content, b.user_Name, bi.image_url, t.tag_id, t.tag_name "
                + "FROM Blogs b "
                + "LEFT JOIN blog_images bi ON b.blog_Id = bi.blog_id "
                + "LEFT JOIN blog_tags bt ON b.blog_Id = bt.blog_id "
                + "LEFT JOIN tags t ON bt.tag_id = t.tag_id "
                + "ORDER BY b.created_At DESC";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
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

    // Method to get a blog by its ID
    public Blog getBlogById(int blogId) {
        Blog blog = null;
        String sql = "SELECT b.blog_Id, b.title, b.content, b.user_Name, bi.image_url, t.tag_id, t.tag_name "
                + "FROM Blogs b "
                + "LEFT JOIN blog_images bi ON b.blog_Id = bi.blog_id "
                + "LEFT JOIN blog_tags bt ON b.blog_Id = bt.blog_id "
                + "LEFT JOIN tags t ON bt.tag_id = t.tag_id "
                + "WHERE b.blog_Id = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, blogId); // Bind the blogId parameter
            ResultSet rs = st.executeQuery();

            // Temporary storage for blog data
            Set<String> imageUrls = new HashSet<>(); // Use a Set for unique image URLs
            List<Tag> tags = new ArrayList<>(); // Use a List for unique Tags

            while (rs.next()) {
                // Create the Blog object only once
                if (blog == null) {
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    String createdBy = rs.getString("user_Name");

                    // Create the Blog object
                    blog = new Blog(blogId, title, content, createdBy, new ArrayList<>(), new ArrayList<>());
                }

                // Add imageUrl if available
                String imageUrl = rs.getString("image_url");
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    imageUrls.add(imageUrl); // Use set to ensure uniqueness
                }

                // Check if tagId is not null
                int tagId = rs.getInt("tag_id");
                if (tagId > 0) {
                    String tagName = rs.getString("tag_name");
                    if (tagName != null && !tagName.isEmpty()) {
                        Tag tag = new Tag(tagId, tagName); // Create Tag object
                        tags.add(tag); // Add Tag to list
                    }
                }
            }

            // Set the tags and image URLs
            if (blog != null) {
                blog.setTags(tags); // Get unique tags from the list
                blog.setImageUrls(new ArrayList<>(imageUrls)); // Convert Set back to List for image URLs
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return blog; // Return the blog
    }

    // Method to get all tags from the database
    public List<Tag> getAllTags() {
        List<Tag> tags = new ArrayList<>();
        String sql = "SELECT tag_id, tag_name FROM tags";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int tagId = rs.getInt("tag_id");
                String tagName = rs.getString("tag_name");
                tags.add(new Tag(tagId, tagName));
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return tags; // Return the list of tags
    }

    // Method to add a new blog post
    public int addBlog(Blog blog) throws SQLException {
        connection.setAutoCommit(false); // Start transaction
        int generatedId = 0;

        try {
            String sql = "INSERT INTO blogs (title, content, user_name, created_at, updated_at) VALUES (?, ?, ?, GETDATE(), GETDATE())";
            try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, blog.getTitle());
                ps.setString(2, blog.getContent());
                ps.setString(3, blog.getCreatedBy());

                ps.executeUpdate();

                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating blog failed, no ID obtained.");
                }
            }

            // Add images and tags
            addBlogImages(generatedId, blog.getImageUrls());
            addBlogTags(generatedId, blog.getTags());

            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            System.err.println("Error saving blog: " + ex.getMessage());
            throw ex;
        } finally {
            connection.setAutoCommit(true);
        }

        return generatedId;
    }

    private void addBlogImages(int blogId, List<String> imageUrls) throws SQLException {
        String sql = "INSERT INTO blog_images (blog_id, image_url) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (String imageUrl : imageUrls) {
                ps.setInt(1, blogId);
                ps.setString(2, imageUrl);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addBlogTags(int blogId, List<Tag> tags) throws SQLException {
        String sql = "INSERT INTO blog_tags (blog_id, tag_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Tag tag : tags) {
                ps.setInt(1, blogId);
                ps.setInt(2, tag.getTagId());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}
