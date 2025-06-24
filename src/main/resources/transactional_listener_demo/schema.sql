CREATE TABLE IF NOT EXISTS transactional_listener_demo_account (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  balance DECIMAL(10,2) NOT NULL
); 