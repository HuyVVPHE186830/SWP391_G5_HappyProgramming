<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Create a New Blog Post</title>

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>

        <!-- HEADER -->
        <jsp:include page="header.jsp"/>

        <!-- Main content -->
        <div class="container mt-5">
            <h1>Create a New Blog Post</h1>

            <form action="submitBlog" method="POST" enctype="multipart/form-data">
                <!-- Blog Title -->
                <div class="mb-3">
                    <label for="blogTitle" class="form-label">Blog Title</label>
                    <input type="text" class="form-control" id="blogTitle" name="blogTitle" placeholder="Enter your blog title" required>
                </div>

                <!-- Blog Content -->
                <div class="mb-3">
                    <label for="blogContent" class="form-label">Content</label>
                    <textarea id="blogContent" name="blogContent" class="form-control" placeholder="Write your blog content here..." rows="10" required></textarea>
                </div>

                <!-- Multiple Image Upload -->
                <div class="mb-3">
                    <label for="blogImages" class="form-label">Upload Images</label>
                    <input type="file" class="form-control" id="blogImages" name="blogImages" accept="image/*" multiple>
                    <small class="form-text text-muted">You can upload multiple images.</small>
                </div>

                <!-- Tags -->
                <div class="mb-3">
                    <label for="blogTags" class="form-label">Tags</label>
                    <input type="text" class="form-control" id="blogTags" name="blogTags" placeholder="Add tags, separated by commas">
                    <small class="form-text text-muted">Add relevant tags for your blog post.</small>
                </div>

                <!-- Submit Button -->
                <button type="submit" class="btn btn-primary">Publish Blog</button>
            </form>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
    </body>
</html>
