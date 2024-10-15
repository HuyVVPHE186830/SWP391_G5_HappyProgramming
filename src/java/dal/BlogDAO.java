package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import model.Blog;
import model.Tag;

public class BlogDAO extends DBContext {

    // Method to get all blogs created by a specific user
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
        String sql = "SELECT b.blog_Id, b.title, b.content, b.user_name, bi.image_url, t.tag_id, t.tag_name "
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
            Map<Integer, Tag> tagMap = new HashMap<>(); // Use a Map for unique Tags

            while (rs.next()) {
                // Create the Blog object only once
                if (blog == null) {
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    String createdBy = rs.getString("user_name");

                    // Create the Blog object
                    blog = new Blog(blogId, title, content, createdBy, new ArrayList<>(), new ArrayList<>());
                }

                // Add imageUrl if available
                String imageUrl = rs.getString("image_url");
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    imageUrls.add(imageUrl); // Use set to ensure uniqueness
                }

                // Check if tagId is not null
                Integer tagId = rs.getInt("tag_id");
                if (tagId != null) {
                    String tagName = rs.getString("tag_name");
                    if (tagName != null && !tagName.isEmpty()) {
                        // Check if tag already exists in the map
                        if (!tagMap.containsKey(tagId)) {
                            Tag tag = new Tag(tagId, tagName); // Create Tag object
                            tagMap.put(tagId, tag); // Store in map to ensure uniqueness
                        }
                    }
                }
            }

            // Set the tags and image URLs
            if (blog != null) {
                blog.setTags(new ArrayList<>(tagMap.values())); // Get unique tags from the map
                blog.setImageUrls(new ArrayList<>(imageUrls)); // Convert Set back to List for image URLs
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return blog; // Return the blog
    }

    // Method to add a new blog post
    public void addBlog(Blog blog) {
        String sql = "INSERT INTO Blogs (title, content, user_name, created_At, updated_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, blog.getTitle());
            st.setString(2, blog.getContent());
            st.setString(3, blog.getCreatedBy()); // Use createdBy for user name
            st.executeUpdate();

            // Get the generated blog ID
            ResultSet generatedKeys = st.getGeneratedKeys();
            if (generatedKeys.next()) {
                int blogId = generatedKeys.getInt(1);
                // Insert image URLs
                for (String imageUrl : blog.getImageUrls()) {
                    addImageToBlog(blogId, imageUrl);
                }
                // Insert tags
                for (Tag tag : blog.getTags()) {
                    addTagToBlog(blogId, tag);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    private void addTagToBlog(int blogId, Tag tag) {
        // First, get the tag ID by name (without inserting new tags)
        int tagId = getTagIdByName(tag.getTagName());

        // Only associate the tag if it exists in the database
        if (tagId > 0) {
            String sql = "INSERT INTO blog_tags (blog_id, tag_id) VALUES (?, ?)";
            try {
                PreparedStatement st = connection.prepareStatement(sql);
                st.setInt(1, blogId);
                st.setInt(2, tagId);
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        } else {
            System.out.println("Tag not found in the database: " + tag.getTagName());
        }
    }

    // Method to get tag ID by tag name
    public int getTagIdByName(String tagName) {
        String sql = "SELECT tag_id FROM tags WHERE tag_name = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, tagName);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("tag_id"); // Return tag ID if found
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return 0; // Return 0 if tag not found
    }

    // Method to add a blog and associate it with tags
    public void addBlogWithTags(Blog blog, List<Integer> tagIds) {
        String sql = "INSERT INTO Blogs (title, content, user_name, created_At, updated_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        try {
            PreparedStatement st = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setString(1, blog.getTitle());
            st.setString(2, blog.getContent());
            st.setString(3, blog.getCreatedBy());
            st.executeUpdate();

            // Get the generated blog ID
            ResultSet generatedKeys = st.getGeneratedKeys();
            if (generatedKeys.next()) {
                int blogId = generatedKeys.getInt(1);

                // Add images
                for (String imageUrl : blog.getImageUrls()) {
                    addImageToBlog(blogId, imageUrl);
                }

                // Add tags
                for (int tagId : tagIds) {
                    addTagToBlog(blogId, tagId);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    // Method to add an image to a blog
    private void addImageToBlog(int blogId, String imageUrl) {
        String sql = "INSERT INTO blog_images (blog_id, image_url) VALUES (?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, blogId);
            st.setString(2, imageUrl);
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    // Method to add a tag to a blog
    private void addTagToBlog(int blogId, int tagId) {
        String sql = "INSERT INTO blog_tags (blog_id, tag_id) VALUES (?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, blogId);
            st.setInt(2, tagId);
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public List<Tag> getAllTags() {
        List<Tag> tags = new ArrayList<>();
        String sql = "SELECT tag_id, tag_name FROM tags"; // Adjust table and field names as per your DB schema

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            // Iterate through the result set and create Tag objects
            while (rs.next()) {
                int tagId = rs.getInt("tag_id");
                String tagName = rs.getString("tag_name");

                Tag tag = new Tag(tagId, tagName);
                tags.add(tag);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Proper error handling/logging should be here
        }
        return tags;
    }

    public List<Blog> searchBlogs(String query) {
        List<Blog> list = new ArrayList<>();
        String sql = "SELECT b.blog_Id, b.title, b.content, b.user_Name, bi.image_url, t.tag_id, t.tag_name "
                + "FROM Blogs b "
                + "LEFT JOIN blog_images bi ON b.blog_Id = bi.blog_id "
                + "LEFT JOIN blog_tags bt ON b.blog_Id = bt.blog_id "
                + "LEFT JOIN tags t ON bt.tag_id = t.tag_id "
                + "WHERE b.title LIKE ? OR b.content LIKE ? OR b.blog_Id IN ("
                + "SELECT bt.blog_id FROM blog_tags bt "
                + "INNER JOIN tags tg ON bt.tag_id = tg.tag_id WHERE tg.tag_name LIKE ?) "
                + "ORDER BY b.created_At DESC";

        try {
            PreparedStatement st = connection.prepareStatement(sql);

            String searchQuery = "%" + query + "%"; // Prepare query for SQL LIKE
            st.setString(1, searchQuery);
            st.setString(2, searchQuery);
            st.setString(3, searchQuery);

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

                // Add the current row's imageUrl if available
                String imageUrl = rs.getString("image_url");
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    imageUrls.add(imageUrl);
                }

                // Create a new Tag object if available
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

}
