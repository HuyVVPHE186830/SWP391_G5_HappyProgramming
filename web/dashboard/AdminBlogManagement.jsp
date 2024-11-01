<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Blogs</title>
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<main class="container">
    <section class="mt-4">
        <h3>Manage Blogs</h3>
        <div class="text-right">
            <a href="#addBlogModal" class="btn btn-success" data-toggle="modal">Add Blog</a>
        </div>
        <table class="table table-hover mt-3">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Created Date</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${sessionScope.blogList}" var="blog">
                    <tr>
                        <td>${blog.title}</td>
                        <td>${blog.createdBy}</td>
                        <td>${blog.createdDate}</td>
                        <td>
                            <button class="btn btn-primary" data-toggle="modal" data-target="#editBlogModal"
                                    onclick="populateEditBlogModal(${blog.blogId}, '${blog.title}', '${blog.content}')">Edit</button>
                            <button class="btn btn-danger" data-toggle="modal" data-target="#deleteBlogModal"
                                    onclick="populateDeleteBlogModal(${blog.blogId})">Delete</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </section>
</main>

<!-- Add Blog Modal -->
<div id="addBlogModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="addblog" method="post">
                <div class="modal-header">
                    <h4>Add New Blog</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label>Title</label>
                        <input type="text" name="title" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Content</label>
                        <textarea name="content" class="form-control" rows="5" required></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-success">Add</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Edit Blog Modal -->
<div id="editBlogModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="updateblog" method="post">
                <div class="modal-header">
                    <h4>Edit Blog</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="blogId" id="editBlogId">
                    <div class="form-group">
                        <label>Title</label>
                        <input type="text" name="title" id="editTitle" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Content</label>
                        <textarea name="content" id="editContent" class="form-control" rows="5" required></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Update</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Delete Blog Modal -->
<div id="deleteBlogModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="deleteblog" method="post">
                <div class="modal-header">
                    <h4>Delete Blog</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="blogId" id="deleteBlogId">
                    <p>Are you sure you want to delete this blog?</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-danger">Delete</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function populateEditBlogModal(blogId, title, content) {
        document.getElementById('editBlogId').value = blogId;
        document.getElementById('editTitle').value = title;
        document.getElementById('editContent').value = content;
    }
    
    function populateDeleteBlogModal(blogId) {
        document.getElementById('deleteBlogId').value = blogId;
    }
</script>

<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</body>
</html>
