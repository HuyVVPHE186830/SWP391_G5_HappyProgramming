package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Tag;

public class TagDAO extends DBContext {

    // Method to check if a tag exists by name
    public boolean tagExists(String tagName) {
        String sql = "SELECT COUNT(*) FROM tags WHERE tag_name = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, tagName);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if tag exists
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false; // Returns false if an error occurs or tag doesn't exist
    }

    // Method to get a tag by its name
    public Tag getTag(String tagName) {
        String sql = "SELECT tag_id, tag_name FROM tags WHERE tag_name = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, tagName);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int tagId = rs.getInt("tag_id");
                return new Tag(tagId, rs.getString("tag_name")); // Return the Tag object
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null; // Return null if the tag doesn't exist
    }

    // Method to add a new tag to the database
    public int addTag(String tagName) {
        String sql = "INSERT INTO tags (tag_name) VALUES (?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setString(1, tagName);
            st.executeUpdate();

            // Get the generated tag ID
            ResultSet generatedKeys = st.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1); // Return the new tag ID
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return 0; // Return 0 if the tag could not be added
    }

    // Method to retrieve or add a tag, returning its ID
    public int getOrAddTag(String tagName) {
        if (tagExists(tagName)) {
            Tag tag = getTag(tagName);
            return tag != null ? tag.getTagId() : 0; // Return the existing tag ID
        } else {
            return addTag(tagName); // Add new tag and return its ID
        }
    }
}
