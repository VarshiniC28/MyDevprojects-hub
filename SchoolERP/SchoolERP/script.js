document.addEventListener('DOMContentLoaded', function() {
  console.log('School Management System Loaded!');
});

document.getElementById('loginForm').addEventListener('submit', function(e) {
  e.preventDefault();
  const role = document.getElementById('role').value;
  
  if (role === "admin") {
    window.location.href = "admin_dashboard.html";
  } else if (role === "faculty") {
    window.location.href = "faculty_dashboard.html";
  } else if (role === "student") {
    window.location.href = "student_dashboard.html";
  } else {
    alert('Please select a role.');
  }
});
