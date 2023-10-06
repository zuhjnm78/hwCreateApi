SELECT student.name, student.age, faculty.name AS faculty_name
FROM student
LEFT JOIN faculty ON student.faculty_id = faculty.id;

SELECT student.name, student.age
FROM student
JOIN student_avatar ON student.id = student_avatar.student_id;