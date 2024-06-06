<?php

require_once 'dbconfig.in.php';

if ($_SERVER['REQUEST_METHOD'] === "POST") {
    $action = $_POST["action"];

    if ($action === "login") {
        login();
    } else if ($action === "reset_password") {
        resetPassword();
    }
}

function login() {
    global $pdo;

    $email = $_POST["email"];
    $password = $_POST["password"];

    $errors = [];
    if (empty($email)) {
        $errors[] = "Invalid email!!!";
    }
    if (empty($password)) {
        $errors[] = "Password is required.";
    }

    if (count($errors) > 0) {
        echo json_encode(["status" => "error", "messages" => $errors]);
        exit();
    }

    try {
        $sql = "SELECT id, type, password FROM account WHERE email = :email";
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':email', $email);
        $stmt->execute();
        $Account = $stmt->fetch();
        if ($Account && $password == $Account['password']) {
            echo json_encode(["status" => "success", "message" => "Login successful.", "Account_id" => $Account['id'], "Account_type" => $Account["type"]]);
        } else {
            echo json_encode(["status" => "error", "message" => "Invalid email or password."]);
        }
    } catch (PDOException $e) {
        echo json_encode(["status" => "error", "message" => $e->getMessage()]);
    }
}

function resetPassword() {
    global $pdo;

    $email = $_POST["email"];
    $newPassword = $_POST["new_password"];
    $confirmPassword = $_POST["confirm_password"];

    $errors = [];
    if (empty($email)) {
        $errors[] = "Invalid email!!!";
    }
    if (empty($newPassword)) {
        $errors[] = "New password is required.";
    }
    if ($newPassword !== $confirmPassword) {
        $errors[] = "Passwords do not match.";
    }

    if (count($errors) > 0) {
        echo json_encode(["status" => "error", "messages" => $errors]);
        exit();
    }

    try {
        $sql = "SELECT id FROM account WHERE email = :email";
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':email', $email);
        $stmt->execute();
        $Account = $stmt->fetch();

        if ($Account) {
            $sql = "UPDATE account SET password = :password WHERE email = :email";
            $stmt = $pdo->prepare($sql);
            $stmt->bindValue(':password', $newPassword);
            $stmt->bindValue(':email', $email);
            $stmt->execute();

            echo json_encode(["status" => "success", "message" => "Password has been reset."]);
        } else {
            echo json_encode(["status" => "error", "message" => "Email does not exist."]);
        }
    } catch (PDOException $e) {
        echo json_encode(["status" => "error", "message" => $e->getMessage()]);
    }
}
?>
