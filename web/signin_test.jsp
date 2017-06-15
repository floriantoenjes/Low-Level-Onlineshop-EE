<jsp:useBean id="customer" class="com.floriantoenjes.learning.model.Customer" scope="session"></jsp:useBean>
<jsp:setProperty name="customer" property="email" value="admin@email.com"></jsp:setProperty>
<jsp:forward page="signin.jsp"></jsp:forward>