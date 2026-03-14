--CREATE DATABASE J6Security2;
--GO
--Use J6Security2;
--go
-- 3. Tạo bảng J6roles (Lưu ý: Tạo bảng này trước vì nó không phụ thuộc ai)
CREATE TABLE J6roles (
    Id VARCHAR(50) NOT NULL PRIMARY KEY,
    Name NVARCHAR(50) NOT NULL
);
GO

-- 4. Tạo bảng J6users
CREATE TABLE J6users (
    Username VARCHAR(50) NOT NULL PRIMARY KEY,
    Password VARCHAR(255) NOT NULL,
    Enabled BIT NOT NULL
);
GO



-- 5. Tạo bảng J6userroles (Bảng trung gian, có khóa ngoại trỏ về 2 bảng trên)
CREATE TABLE J6userroles (
    Id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    Username VARCHAR(50) NOT NULL,
    RoleId VARCHAR(50) NOT NULL,
    CONSTRAINT FK_UserRoles_Users FOREIGN KEY (Username) REFERENCES J6users(Username) ON DELETE CASCADE,
    CONSTRAINT FK_UserRoles_Roles FOREIGN KEY (RoleId) REFERENCES J6roles(Id) ON DELETE CASCADE
);
GO

-- Thêm Users
INSERT INTO J6users (Username, Password, Enabled) VALUES
('dtanhieu123@gmail.com', '{noop}123', 1),
('user@gmail.com', '{noop}123', 1),
('admin@gmail.com', '{noop}123', 1),
('both@gmail.com', '{noop}123', 1);
GO

-- Thêm Roles (Dùng N trước chuỗi để lưu tiếng Việt có dấu)
INSERT INTO J6roles (Id, Name) VALUES
('ROLE_USER', N'Nhân viên'),
('ROLE_ADMIN', N'Quản lý');
GO


-- Thêm UserRoles (Phân quyền)
-- Lưu ý: Cột Id tự động tăng nên không cần insert
INSERT INTO J6userroles (Username, RoleId) VALUES
('dtanhieu123@gmail.com', 'ROLE_ADMIN'),
('user@gmail.com', 'ROLE_USER'),
('admin@gmail.com', 'ROLE_ADMIN'),
('both@gmail.com', 'ROLE_ADMIN');
GO
