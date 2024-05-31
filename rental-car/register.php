<?php
require_once 'dbconfig.in.php';

if ($_SERVER['REQUEST_METHOD'] === "POST") {
    $email = $_POST["email"];
    $name = $_POST["name"];
    $password = $_POST["password"];
    $gender = $_POST["gender"];
    $phone = $_POST["phone"];
    
    $errors = [];
    
    if (empty($email) || !filter_var($email, FILTER_VALIDATE_EMAIL)) {
        $errors[] = "Invalid email.";
    }
    if (empty($name)) {
        $errors[] = "Name is required.";
    }
    if (empty($password)) {
        $errors[] = "Password is required.";
    } else {
        if (strlen($password) < 8) {
            $errors[] = "Password must be at least 8 characters long.";
        }
        if (!preg_match('/[A-Za-z]/', $password)) {
            $errors[] = "Password must contain at least one letter.";
        }
        if (!preg_match('/[0-9]/', $password)) {
            $errors[] = "Password must contain at least one number.";
        }
        if (!preg_match('/[!@#$%^&*(),.?":{}|<>]/', $password)) {
            $errors[] = "Password must contain at least one special character.";
        }
    }
    if (empty($gender)) {
        $errors[] = "Gender is required.";
    }
    if (empty($phone)) {
        $errors[] = "Phone number is required.";
    }
    try {
        $checkEmailSql = "SELECT COUNT(*) FROM Account WHERE email = :email";
        $checkEmailStmt = $pdo->prepare($checkEmailSql);
        $checkEmailStmt->bindValue(':email', $email);
        $checkEmailStmt->execute();
        $emailExists = $checkEmailStmt->fetchColumn();

        if ($emailExists > 0) {
            $errors[] = "Email is already used.";
        }

        if (count($errors) > 0) {
            echo json_encode(["status" => "error", "messages" => $errors]);
            exit();
        }
        $hashedPassword = password_hash($password, PASSWORD_DEFAULT);
        $sql = "INSERT INTO Account (email, name, password, gender, phone) VALUES (:email, :name, :password, :gender, :phone)";
        $stmt = $pdo->prepare($sql);
        
        $stmt->bindValue(':email', $email);
        $stmt->bindValue(':name', $name);
        $stmt->bindValue(':password', $hashedPassword);
        $stmt->bindValue(':gender', $gender);
        $stmt->bindValue(':phone', $phone);

        if ($stmt->execute()) {
            echo json_encode(["status" => "success", "message" => "User registered successfully."]);
        } else {
            echo json_encode(["status" => "error", "message" => "User registration failed."]);
        }
    } catch (PDOException $e) {
        echo json_encode(["status" => "error", "message" => $e->getMessage()]);
    }
}
?>
