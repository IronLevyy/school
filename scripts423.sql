SELECT s.name AS student_name, s.age, f.name AS faculty_name
FROM student s
    JOIN faculty f ON s.faculty_id = f.id;

SELECT s.name AS student_name, s.fakulty
FROM student s
    JOIN avatar a ON a.student_id = s.id;
