<?php
require_once 'dbconfig.in.php';

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
    echo json_encode(["error" => "Only GET requests are allowed"]);
    exit;
}


try {
    $results = [];
    $stmt = $pdo->query("SELECT COUNT(*) AS TotalCars FROM car");
    $results['TotalCars'] = $stmt->fetch(PDO::FETCH_ASSOC)['TotalCars'];
    $stmt = $pdo->query("SELECT COUNT(*) AS TotalCarsNotRented 
                         FROM car 
                         WHERE status = 'Free'");
    $results['TotalCarsNotRented'] = $stmt->fetch(PDO::FETCH_ASSOC)['TotalCarsNotRented'];
    $stmt = $pdo->query("SELECT COUNT(*) AS RentedCarsNow 
    FROM car 
    WHERE status = 'rent'");
    $results['RentedCarsNow'] = $stmt->fetch(PDO::FETCH_ASSOC)['RentedCarsNow'];
    $stmt = $pdo->query("SELECT SUM(TotalPrice) AS TotalRevenue FROM rent");
    $results['TotalRevenue'] = $stmt->fetch(PDO::FETCH_ASSOC)['TotalRevenue'];
    $results['TotalRevenue'] = $results['TotalRevenue'] ? $results['TotalRevenue'] : 0;

    $stmt = $pdo->query("SELECT COUNT(*) AS TotalCustomers FROM account");
    $results['TotalCustomers'] = $stmt->fetch(PDO::FETCH_ASSOC)['TotalCustomers'];

    $stmt = $pdo->query("SELECT COUNT(*) AS NewCustomers 
                         FROM account 
                         WHERE DATEDIFF(CURDATE(), DATE_SUB(CURDATE(), INTERVAL 30 DAY)) <= 300");
    $results['NewCustomers'] = $stmt->fetch(PDO::FETCH_ASSOC)['NewCustomers'];

    $stmt = $pdo->query("SELECT COUNT(DISTINCT AccountID) AS CustomersRentedOneCar 
                         FROM rent");
    $results['CustomersRentedOneCar'] = $stmt->fetch(PDO::FETCH_ASSOC)['CustomersRentedOneCar'];

    // Top car (most rented car)
    $stmt = $pdo->query("
    SELECT  car.company AS Company , car.Model_year AS model, COUNT(rent.carID) AS RentCount
    FROM rent
    JOIN car ON rent.carID = car.ID
    GROUP BY rent.carID
    ORDER BY RentCount DESC
    LIMIT 5
");
$topCar = $stmt->fetchAll();  
    $results['TopCar'] = $topCar ? $topCar : array(["Company" => null, "model"=>null, "RentCount" => 0] );


// Top Customer (most Customer rent cars)
    $stmt = $pdo->query("
    SELECT  account.name AS Name , account.email AS Email, COUNT(rent.	AccountID) AS RentCount
    FROM rent
    JOIN account ON rent.AccountID = account.ID
    GROUP BY rent.AccountID
    ORDER BY RentCount DESC
    LIMIT 5
");
$topCustomer = $stmt->fetchAll();  
    $results['TopCustomer'] = $topCustomer ? $topCustomer : array(["Name" => null, "Email"=>null, "RentCount" => 0] );

    echo json_encode($results);

} catch (PDOException $e) {
    echo json_encode(["error" => "Database error: " . $e->getMessage()]);
}
?>
