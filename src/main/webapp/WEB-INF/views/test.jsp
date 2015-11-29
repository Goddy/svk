<%@ taglib prefix="cl" uri="http://cloudinary.com/jsp/taglib" %>
<%--
  Created by IntelliJ IDEA.
  User: u0090265
  Date: 11/28/15
  Time: 7:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>

<form method="POST" enctype="multipart/form-data" action="upload">
    File to upload: <input type="file" name="file"><br/> Name: <input
        type="text" name="name"><br/> <br/> <input type="submit"
                                                   value="Upload"> Press here to upload the file!
    ${image}
</form>
</body>
</html>