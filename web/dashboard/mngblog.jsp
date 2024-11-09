<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1">
        <title>Manage Blogs</title>
        <link rel="icon" href="images/logo1.png"/>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #fbfbfb;
                margin: 0;
                padding: 0;
            }
            .container-main {
                max-width: 2400px;
                padding-top: 20px;
            }
            .text_page_head {
                font-size: 18px;
                font-weight: 600;
            }
            .text_page {
                font-size: 14px;
                font-weight: 600;
            }
            .sidebar {
                position: fixed;
                top: 0;
                bottom: 0;
                left: 0;
                padding: 58px 0 0;
                width: 240px;
                z-index: 600;
                box-shadow: 0 2px 5px rgb(0 0 0 / 5%), 0 2px 10px rgb(0 0 0 / 5%);
            }
            main {
                padding-left: 240px;
            }
            .truncate-text {
                max-width: 150px;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
            }
            .image-preview {
                width: 50px;
                height: 50px;
                object-fit: cover;
            }
        </style>
    </head>
    <body>
        <!-- Sidebar -->
        <header>
            <jsp:include page="leftadmin.jsp"></jsp:include>
            </header>

            <!-- Main layout -->
            <main>
                <div class="container container-main pt-4">
                    <section class="mb-4">
                        <div class="card">
                            <div class="row p-4">
                                <div class="col-sm-4 text-center">
                                    <h3><strong>Manage Blogs</strong></h3>
                                </div>
                                <div class="col-lg-2"></div>
                                <div class="col-lg-6 text-center">
                                    <form action="<%= request.getContextPath() %>/ManagerBlog" method="post" class="form-inline justify-content-center">
                                    <input name="valueSearch" value="${requestScope.searchValue != null ? requestScope.searchValue : ''}" id="searchId" type="text" class="form-control" placeholder="Search blog" style="width: 60%; border-radius: 15px;">
                                    <button type="submit" class="btn btn-primary ml-2"><i class="fa fa-search"></i></button>
                                </form>
                            </div>
                        </div>
                        <c:if test="${error != null}">
                            <div class="alert alert-danger" role="alert">${error}</div>
                        </c:if>
                        <c:if test="${mess != null}">
                            <div class="alert alert-success" role="alert">${mess}</div>
                        </c:if>
                        <div class="card-body p-0">
                            <div class="table-responsive">
                                <table class="table table-hover text-nowrap">
                                    <thead>
                                        <tr>
                                            <th class="text_page_head">Title</th>
                                            <th class="text_page_head">Image</th>
                                            <th class="text_page_head">Tags</th>
                                            <th class="text_page_head">Content</th>
                                            <th class="text_page_head">Author</th>
                                            <th class="text_page_head">Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${sessionScope.blogList}" var="blog">
                                            <tr>
                                                <td class="text_page truncate-text">${blog.title}</td>
                                                <td>
                                                    <c:forEach items="${blog.imageUrls}" var="image">
                                                        <img src="${image}" alt="Blog Image" class="image-preview">
                                                    </c:forEach>
                                                </td>
                                                <td>
                                                    <c:forEach items="${blog.tags}" var="tag" varStatus="last">
                                                        <span class="badge badge-info">${tag.tagName}</span><c:if test="${!last.last}">, </c:if>
                                                    </c:forEach>
                                                </td>
                                                <td class="text_page truncate-text">${blog.content}</td>
                                                <td class="text_page">${blog.createdBy}</td>
                                                <td>
                                                    <button class="btn btn-primary" data-toggle="modal" data-target="#editBlogModal"
                                                            onclick="populateEditBlogModal(${blog.blogId}, '${blog.title}', '${blog.content}',
                                                                            '<c:forEach items="${blog.tags}" var="tag">${tag.tagName}, </c:forEach>'.slice(0, -2),
                                                                                            '${blog.imageUrls}')"><i class="fa fa-edit"></i></button>
                                                    <button class="btn btn-danger" data-toggle="modal" data-target="#deleteBlogModal"
                                                            onclick="populateDeleteBlogModal(${blog.blogId})"><i class="fa fa-trash"></i></button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </main>

        <!-- Edit Blog Modal -->
        <div id="editBlogModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <!-- Add enctype to support multipart form data -->
                    <form action="UpdateBlogControl" method="post" enctype="multipart/form-data">
                        <div class="modal-header">
                            <h4>Edit Blog</h4>
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="blogId" id="editBlogId">

                            <!-- Title Input -->
                            <div class="form-group">
                                <label>Title</label>
                                <input type="text" name="title" id="editTitle" class="form-control" required>
                            </div>

                            <!-- Content Input -->
                            <div class="form-group">
                                <label>Content</label>
                                <textarea name="content" id="editContent" class="form-control" rows="5" required></textarea>
                            </div>

                            <!-- Tags Input -->
                            <div class="form-group">
                                <label>Tags</label>
                                <input type="text" name="tags" id="editTags" class="form-control" placeholder="Enter tags separated by commas">
                            </div>

                            <!-- Current Images -->
                            <div class="form-group">
                                <label>Current Images</label>
                                <div id="editImageList">
                                    <!-- Existing images will be displayed here -->
                                </div>
                            </div>

                            <!-- New Image Uploads -->
                            <div class="form-group">
                                <label>Upload New Images</label>
                                <input type="file" name="images" class="form-control-file" multiple>
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
            function populateEditBlogModal(blogId, title, content, tags, imageUrls) {
                document.getElementById('editBlogId').value = blogId;
                document.getElementById('editTitle').value = title;
                document.getElementById('editContent').value = content;

                // Populate tags as a comma-separated list
                document.getElementById('editTags').value = tags;

                // Display images for editing
                const imageContainer = document.getElementById('editImages');
                imageContainer.innerHTML = '';
                imageUrls.split(',').forEach(url => {
                    const img = document.createElement('img');
                    img.src = url.trim();
                    img.classList.add('image-preview', 'mr-2');
                    imageContainer.appendChild(img);
                });
            }

            function populateDeleteBlogModal(blogId) {
                document.getElementById('deleteBlogId').value = blogId;
            }
        </script>
    </body>
</html>
