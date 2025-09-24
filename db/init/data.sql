-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th9 24, 2025 lúc 01:30 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `sports`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `address_user`
--

CREATE TABLE `address_user` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `address_user`
--

INSERT INTO `address_user` (`id`, `address`, `phone`, `user_id`) VALUES
(1, 'Bắc Ninh Hà Nội', '0373187183', 2),
(2, 'Bắc Ninh', '0373187182', 4),
(3, 'Bắc Ninh', '0373187182', 7);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `brand_product`
--

CREATE TABLE `brand_product` (
  `id` bigint(20) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name_brand` varchar(255) DEFAULT NULL,
  `status` enum('Active','Closed') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `brand_product`
--

INSERT INTO `brand_product` (`id`, `image`, `name_brand`, `status`) VALUES
(1, 'https://cdn.shopify.com/s/files/1/0456/5070/6581/files/ALLBRANDS_NIKE.jpg?v=1715164070', 'Nike', NULL),
(2, 'https://cdn.shopify.com/s/files/1/0456/5070/6581/files/ALLBRANDS_DAS.jpg?v=1715164070', 'Adidas', NULL),
(3, 'https://cdn.shopify.com/s/files/1/0456/5070/6581/files/HOKA_30296b60-fa58-4266-87ef-6d6916626d47.jpg?v=1715164070', 'Hoka', NULL),
(4, 'https://cdn.shopify.com/s/files/1/0456/5070/6581/files/ALLBRANDS_FILA.jpg?v=1715164070', 'Fila', NULL),
(5, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756536278/8e01a19e-bb7e-46c5-a2d7-27b5c2e8e92d.jpg', 'GUCCI', 'Active'),
(6, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756549492/443731c5-39e7-4518-9f8a-777bbb334ee7.svg', 'ADIDAS', 'Active'),
(7, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756549493/5ffc9fc2-5824-40d2-8363-0d657962b4c5.svg', 'ADIDAS', 'Closed'),
(8, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756549514/48edc3ff-dc3f-421d-af5e-b262973e574c.png', 'BALENCIAGA', 'Active'),
(9, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756549529/70905aff-60e5-4813-b33f-a42768839112.png', 'CHANEL', 'Active'),
(10, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756549546/15e842d7-27af-40b4-82d4-e2cdb2d70395.jpg', 'CONVERSE', 'Active'),
(11, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756549564/50e02a84-dd9a-40f4-93e8-ac31938b8901.png', 'DIOR', 'Active'),
(12, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756549601/3321b4a3-ac3b-4b4f-9cbf-c57e96b279b0.jpg', 'LOUIS VUITON', 'Active'),
(13, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756549620/b9cfdc80-c446-4c0d-93c1-c90a2869a6e8.jpg', 'PUMA', 'Active'),
(14, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756549635/cf5be27b-9b59-4e4b-946f-37c44842f7a3.jpg', 'NIKE', 'Active'),
(15, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756549651/3e4375f0-3a64-4f02-bae9-67283f08e52e.png', 'VANS', 'Active');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cart`
--

CREATE TABLE `cart` (
  `id` bigint(20) NOT NULL,
  `quantity` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `cart`
--

INSERT INTO `cart` (`id`, `quantity`, `product_id`, `user_id`) VALUES
(12, 1, 832, 1),
(13, 1, 1, 3),
(34, 1, 832, 7);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `category_product`
--

CREATE TABLE `category_product` (
  `id` bigint(20) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name_category` varchar(255) DEFAULT NULL,
  `status` enum('Active','Closed') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `category_product`
--

INSERT INTO `category_product` (`id`, `image`, `name_category`, `status`) VALUES
(5, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756536304/d4cf40a1-31a1-4a50-9a65-0ce803f0b929.jpg', 'Thời Trang Nam', 'Closed'),
(6, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756549683/81830383-6a59-42e7-b2ed-de0094475742.jpg', 'Áo khoác', 'Active'),
(7, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756549695/d45f5983-3b45-4851-aa81-92fb91ee4f40.jpg', 'Áo polo', 'Active'),
(8, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756549709/52503451-ea53-430c-9fdf-ea576e77cda7.jpg', 'Áo sơ mi', 'Active'),
(9, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756549723/c29ebda6-58e9-4df6-9112-400d576a11e4.jpg', 'Áo thun', 'Active'),
(10, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756549738/2230c8c4-9328-4fdd-921b-5e01de6f1732.jpg', 'Dép', 'Active'),
(11, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756549755/160e1d63-2c47-40e7-b861-39d8ee6317c7.jpg', 'Đồ thể thao', 'Active'),
(12, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756549769/05a1140b-afdf-416f-b00e-08a65e589850.jpg', 'Giày', 'Active'),
(13, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756549782/bc714f26-7104-4969-9c9e-145880f6baca.jpg', 'Mũ', 'Active'),
(14, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756549797/12ebff90-1022-425b-b287-144949db23e4.jpg', 'Quần jean', 'Active'),
(15, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756549810/f04cc4f0-d2ba-4ad4-8598-e2d8d5d2b0a4.jpg', 'Váy', 'Active'),
(16, NULL, 'Running', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `comment`
--

CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL,
  `date` datetime(6) DEFAULT NULL,
  `messages` varchar(2000) NOT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `media_url` varchar(255) DEFAULT NULL,
  `admin_reply` varchar(2000) DEFAULT NULL,
  `reply_date` datetime DEFAULT NULL,
  `rate` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `comment`
--

INSERT INTO `comment` (`id`, `date`, `messages`, `product_id`, `user_id`, `media_url`, `admin_reply`, `reply_date`, `rate`) VALUES
(6, '2025-09-15 08:36:08.000000', 'hay', 1, 2, 'https://res.cloudinary.com/dvmuyopwa/image/upload/v1757900170/comments/2025/09/10c7e73f-e328-4fa1-a316-6f2bbea1612d.jpg', NULL, NULL, 5);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `conversations`
--

CREATE TABLE `conversations` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `channel` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `assignee_type` varchar(255) NOT NULL,
  `assignee_id` bigint(20) DEFAULT NULL,
  `last_confidence` double DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `image_product`
--

CREATE TABLE `image_product` (
  `id` bigint(20) NOT NULL,
  `image_link` varchar(255) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `image_product`
--

INSERT INTO `image_product` (`id`, `image_link`, `product_id`) VALUES
(3, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756536370/4a99ce06-430e-44b6-b5c1-6e0badb49d20.jpg', 1),
(24, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756552310/a1942b7e-df94-4e57-8278-0007996b345f.jpg', 2),
(30, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756553831/b82ade6d-0179-4cb1-8058-9c5d92b11888.webp', 832),
(31, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756553868/3bab5c29-20a9-413d-a0ca-f330be4248f6.jpg', 833),
(32, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756553887/5455e914-ade6-4411-843f-90ff2c2b1bdc.webp', 834),
(33, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756553903/b44e5a4b-b6dd-43be-901b-e123c8d1db30.jpg', 835),
(34, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756553967/c3194c2d-78ce-4185-ac99-b29d4e806b77.webp', 836),
(35, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756553988/b9e20f19-f776-4d2d-998f-86774904e582.jpg', 837),
(36, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756554010/44b2a1b5-2a0c-4c0e-95da-c41ba80d58fc.webp', 838),
(37, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756554021/b533b3c1-f467-4f04-99c1-64589485fa3e.jpg', 839),
(38, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756558585/80e75227-490f-4561-ba1e-c0f39595cda1.webp', 840),
(39, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756558606/93ac5c74-a857-4de2-b2fd-5774fb97cc84.jpg', 841),
(40, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756558621/85333d71-fe03-4a61-9ef0-fe711467576e.webp', 842),
(41, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756558637/ef6959d2-9ebb-47ce-969b-585f0782795f.jpg', 843),
(42, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756558671/4ede0fa4-4285-495e-b23a-d35a4313bd3e.jpg', 844),
(43, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756558691/622cadf5-91d3-433e-932c-ce0b470a0053.webp', 845),
(44, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756558711/9a0d3f8b-9bf8-49db-a794-913dc0b9f1b9.jpg', 846),
(45, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756558727/07bf44e4-40d7-4104-9893-a9ebee2d3025.webp', 847),
(46, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756558745/29bc0eb0-bd60-485f-aeb7-03e48932ddda.jpg', 848),
(47, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756558765/0490b6e2-44a4-4060-b212-75473e6e9da8.webp', 849),
(48, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756558783/e0625316-af00-49f2-a7d7-3995844e85a0.jpg', 850),
(49, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756558799/ce943e73-2fd0-4672-9701-1270b9dd0599.webp', 851),
(50, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756558824/58dc5850-8c30-4c8f-8292-9b352548e9af.jpg', 852),
(51, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756558844/4e97c25e-93ca-4126-9aa2-5df6a48d8d35.jpg', 853),
(52, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756558872/ff51780c-546d-475e-a72c-e32e59bbc0f9.jpg', 855),
(53, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756558890/ad7057dc-9e0f-4e12-a3cd-dc1505fccdcc.jpg', 854),
(54, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756558905/dfd5bd18-f356-4f21-a572-88d38eb30ace.webp', 856),
(55, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756558921/d6ff9788-dbeb-4bda-adf8-149418caed87.webp', 857),
(56, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756558938/db40f6a7-c7c6-4b64-9445-49f24e1f4af0.webp', 858),
(57, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756558960/f96f99a8-f9b4-4443-8fee-8b83e1697e1b.webp', 859),
(58, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756558980/6888e82a-8472-4eff-90e0-eb4a51692e3e.webp', 860),
(59, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756558997/1d0fbba4-7081-443d-b904-ff9dddc5017e.jpg', 861),
(60, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756559013/d0d559aa-5195-4842-9fd7-e8f6516d3d20.webp', 862),
(61, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756559037/267bb1b2-ed04-43ff-8e15-eb5dbd5e9d30.jpg', 863),
(62, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756559059/3242583a-0b50-48ba-aeb3-30419302c3de.jpg', 864),
(63, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756559077/57b55d7b-6162-4db9-8bb8-9a779b857843.jpg', 865),
(64, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756559096/c54f4eb7-7863-4567-845e-d63f8a5f31f0.jpg', 866),
(65, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756559111/befb1a5a-0035-44d0-bdd7-bf5438873390.jpg', 867),
(66, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756559135/a1a5dd25-e70c-416a-adcc-43e6ebd2ccef.png', 868),
(67, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756559152/4f6fd882-0899-4a45-bdfe-239860d4ec31.jpg', 869),
(68, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756559174/f8e9305f-9cf4-4c91-8848-dc0aeb0fcf49.png', 870),
(69, 'http://res.cloudinary.com/dvmuyopwa/image/upload/v1756559183/e9219263-52a0-4b03-8078-129ea1cf8f26.jpg', 871),
(70, 'https://res.cloudinary.com/dvmuyopwa/image/upload/v1757899458/a1a506ec-9918-4aed-94bb-1d6f1dd034c8.jpg', 847),
(71, 'https://res.cloudinary.com/dvmuyopwa/image/upload/v1757899459/e21c8c43-63ea-4e03-afe4-5555d3943875.jpg', 847);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `messages`
--

CREATE TABLE `messages` (
  `id` bigint(20) NOT NULL,
  `conversation_id` bigint(20) NOT NULL,
  `sender_type` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `token_in` int(11) DEFAULT NULL,
  `token_out` int(11) DEFAULT NULL,
  `cost_estimate` double DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orders`
--

CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `quantity_product` bigint(20) DEFAULT NULL,
  `status_order` enum('Dang_Xu_Ly','Dang_Giao','Da_Giao','Yeu_Cau_Huy','Da_Huy') NOT NULL,
  `total` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `orders`
--

INSERT INTO `orders` (`id`, `address`, `date`, `phone`, `quantity_product`, `status_order`, `total`, `user_id`) VALUES
(7, 'Bắc Ninh Hà Nội', '2025-08-30 14:14:07.000000', '0373187183', 1, 'Da_Giao', 4500000, 2),
(43, 'Bắc Ninh', '2025-08-31 19:18:25.000000', '0373187182', 3, 'Da_Giao', 31680000, 4),
(44, 'Bắc Ninh Hà Nội', '2025-09-04 20:44:51.000000', '0373187183', 1, 'Da_Giao', 10750000, 2),
(45, 'Bắc Ninh Hà Nội', '2025-09-04 20:50:21.000000', '0373187183', 3, 'Da_Giao', 2700000, 2),
(46, 'Bắc Ninh Hà Nội', '2025-09-04 22:35:27.000000', '0373187183', 1, 'Da_Giao', 10560000, 2),
(47, 'Bắc Ninh Hà Nội', '2025-09-04 22:58:21.000000', '0373187183', 3, 'Da_Giao', 2700000, 2),
(48, NULL, '2025-09-04 23:01:11.000000', NULL, 3, 'Da_Giao', 2700000, 2),
(49, 'Bắc Ninh Hà Nội', '2025-09-04 23:02:16.000000', '0373187183', 2, 'Da_Giao', 20160000, 2),
(50, 'Bắc Ninh Hà Nội', '2025-09-05 14:11:39.000000', '0373187183', 1, 'Da_Giao', 10560000, 2),
(51, 'Bắc Ninh Hà Nội', '2025-09-05 14:28:34.000000', '0373187183', 1, 'Da_Giao', 900000, 2),
(52, 'Bắc Ninh Hà Nội', '2025-09-05 15:29:19.000000', '0373187183', 1, 'Da_Giao', 900000, 2),
(53, 'Bắc Ninh Hà Nội', '2025-09-11 09:05:40.000000', '0373187183', 1, 'Da_Giao', 10080000, 2),
(54, 'Bắc Ninh Hà Nội', '2025-09-11 09:31:17.000000', '0373187183', 3, 'Da_Giao', 30880000, 2),
(55, 'Bắc Ninh Hà Nội', '2025-09-11 10:08:35.000000', '0373187183', 2, 'Da_Giao', 1800000, 2),
(56, 'Bắc Ninh Hà Nội', '2025-09-11 10:20:42.000000', '0373187183', 4, 'Da_Giao', 3600000, 2),
(57, 'Bắc Ninh Hà Nội', '2025-09-11 10:33:13.000000', '0373187183', 4, 'Da_Giao', 3600000, 2),
(58, 'Bắc Ninh Hà Nội', '2025-09-15 13:48:07.000000', '0373187183', 2, 'Da_Huy', 21120000, 2),
(59, 'Bắc Ninh Hà Nội', '2025-09-22 13:24:22.000000', '0373187183', 4, 'Yeu_Cau_Huy', 41623000, 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_detail`
--

CREATE TABLE `order_detail` (
  `id` bigint(20) NOT NULL,
  `discount` bigint(20) DEFAULT NULL,
  `price` bigint(20) DEFAULT NULL,
  `quantity` bigint(20) DEFAULT NULL,
  `total` bigint(20) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `order_detail`
--

INSERT INTO `order_detail` (`id`, `discount`, `price`, `quantity`, `total`, `order_id`, `product_id`) VALUES
(2, 10, 1000000, 5, 4500000, 7, 1),
(3, NULL, 10560000, 3, 31680000, 43, 832),
(4, NULL, 10750000, 1, 10750000, 44, 838),
(5, NULL, 900000, 3, 2700000, 45, 1),
(6, NULL, 10560000, 1, 10560000, 46, 832),
(7, NULL, 900000, 3, 2700000, 47, 1),
(8, NULL, 900000, 3, 2700000, 48, 1),
(9, NULL, 10080000, 2, 20160000, 49, 833),
(10, NULL, 10560000, 1, 10560000, 50, 832),
(11, NULL, 900000, 1, 900000, 51, 1),
(12, NULL, 900000, 1, 900000, 52, 1),
(13, NULL, 10080000, 1, 10080000, 53, 833),
(14, NULL, 10080000, 1, 10080000, 54, 833),
(15, NULL, 10560000, 1, 10560000, 54, 832),
(16, NULL, 10240000, 1, 10240000, 54, 849),
(17, NULL, 900000, 2, 1800000, 55, 1),
(18, NULL, 900000, 4, 3600000, 56, 1),
(19, NULL, 900000, 4, 3600000, 57, 1),
(20, NULL, 10560000, 2, 21120000, 58, 832),
(21, NULL, 10560000, 1, 10560000, 59, 832),
(22, NULL, 10614000, 1, 10614000, 59, 836),
(23, NULL, 10209000, 1, 10209000, 59, 835),
(24, NULL, 10240000, 1, 10240000, 59, 849);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product`
--

CREATE TABLE `product` (
  `id` bigint(20) NOT NULL,
  `color` varchar(255) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `description` mediumtext DEFAULT NULL,
  `discount` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` bigint(20) DEFAULT NULL,
  `quantity` bigint(20) DEFAULT NULL,
  `quantity_sell` bigint(20) DEFAULT NULL,
  `status_product` enum('Active','Closed') DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `brand_id` bigint(20) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `product`
--

INSERT INTO `product` (`id`, `color`, `create_date`, `description`, `discount`, `name`, `price`, `quantity`, `quantity_sell`, `status_product`, `update_date`, `brand_id`, `category_id`) VALUES
(1, 'Xanh', '2025-08-30 13:46:07.000000', 'Đẹp', 10, 'Áo', 1000000, 84, 16, 'Active', NULL, 5, 5),
(2, 'Xanh than', '2025-08-30 18:11:49.000000', 'Luôn sẵn sàng vận động. Chiếc áo polo adidas này giúp bạn luôn cảm thấy khô thoáng bất kể ở phòng gym, khi chạy việc vặt hay trong văn phòng.', 0, 'Áo polo 3 sọc', 120000, 100, 0, 'Active', NULL, 6, 7),
(832, 'Đen', '2025-08-30 18:35:47.000000', 'Mẫu GUCCI - Áo khoác Classic', 12, 'GUCCI Áo khoác Classic', 12000000, 14, 5, 'Active', '2025-08-30 18:35:47.000000', 5, 6),
(833, 'Xanh', '2025-08-30 18:35:47.000000', 'Mẫu CHANEL - Áo khoác Classic', 16, 'CHANEL Áo khoác Classic', 12000000, 14, 5, 'Active', '2025-08-30 18:35:47.000000', 9, 6),
(834, 'Trắng', '2025-08-30 18:35:47.000000', 'Mẫu GUCCI - Áo khoác Edition', 13, 'GUCCI Áo khoác Edition', 12300000, 18, 3, 'Active', '2025-08-30 18:35:47.000000', 5, 6),
(835, 'Đỏ', '2025-08-30 18:35:47.000000', 'Mẫu CHANEL - Áo khoác Edition', 17, 'CHANEL Áo khoác Edition', 12300000, 17, 4, 'Active', '2025-08-30 18:35:47.000000', 9, 6),
(836, 'Trắng', '2025-08-30 18:35:47.000000', 'Mẫu GUCCI - Áo polo Classic', 13, 'GUCCI Áo polo Classic', 12200000, 17, 4, 'Active', '2025-08-30 18:35:47.000000', 5, 7),
(837, 'Đỏ', '2025-08-30 18:35:47.000000', 'Mẫu CHANEL - Áo polo Classic', 17, 'CHANEL Áo polo Classic', 12200000, 18, 3, 'Active', '2025-08-30 18:35:47.000000', 9, 7),
(838, 'Be', '2025-08-30 18:35:47.000000', 'Mẫu GUCCI - Áo polo Edition', 14, 'GUCCI Áo polo Edition', 12500000, 1, 4, 'Active', '2025-09-04 20:44:22.000000', 5, 7),
(839, 'Đen', '2025-08-30 18:35:47.000000', 'Mẫu CHANEL - Áo polo Edition', 18, 'CHANEL Áo polo Edition', 12500000, 19, 4, 'Active', '2025-08-30 18:35:47.000000', 9, 7),
(840, 'Be', '2025-08-30 18:35:47.000000', 'Mẫu GUCCI - Áo sơ mi Classic', 14, 'GUCCI Áo sơ mi Classic', 12400000, 19, 4, 'Active', '2025-08-30 18:35:47.000000', 5, 8),
(841, 'Đen', '2025-08-30 18:35:47.000000', 'Mẫu CHANEL - Áo sơ mi Classic', 18, 'CHANEL Áo sơ mi Classic', 12400000, 19, 4, 'Active', '2025-08-30 18:35:47.000000', 9, 8),
(842, 'Nâu', '2025-08-30 18:35:47.000000', 'Mẫu GUCCI - Áo sơ mi Edition', 15, 'GUCCI Áo sơ mi Edition', 12700000, 20, 0, 'Active', '2025-08-30 18:35:47.000000', 5, 8),
(843, 'Trắng', '2025-08-30 18:35:47.000000', 'Mẫu CHANEL - Áo sơ mi Edition', 19, 'CHANEL Áo sơ mi Edition', 12700000, 20, 0, 'Active', '2025-08-30 18:35:47.000000', 9, 8),
(844, 'Nâu', '2025-08-30 18:35:47.000000', 'Mẫu GUCCI - Áo thun Classic', 15, 'GUCCI Áo thun Classic', 12600000, 20, 0, 'Active', '2025-08-30 18:35:47.000000', 5, 9),
(845, 'Trắng', '2025-08-30 18:35:47.000000', 'Mẫu CHANEL - Áo thun Classic', 19, 'CHANEL Áo thun Classic', 12600000, 20, 0, 'Active', '2025-08-30 18:35:47.000000', 9, 9),
(846, 'Xanh', '2025-08-30 18:35:47.000000', 'Mẫu GUCCI - Áo thun Edition', 16, 'GUCCI Áo thun Edition', 12900000, 21, 1, 'Active', '2025-08-30 18:35:47.000000', 5, 9),
(847, 'Be', '2025-08-30 18:35:47.000000', 'Mẫu CHANEL - Áo thun Edition', 20, 'CHANEL Áo thun Edition', 12900000, 21, 1, 'Active', '2025-08-30 18:35:47.000000', 9, 9),
(848, 'Xanh', '2025-08-30 18:35:47.000000', 'Mẫu GUCCI - Dép Classic', 16, 'GUCCI Dép Classic', 12800000, 21, 1, 'Active', '2025-08-30 18:35:47.000000', 5, 10),
(849, 'Be', '2025-08-30 18:35:47.000000', 'Mẫu CHANEL - Dép Classic', 20, 'CHANEL Dép Classic', 12800000, 19, 3, 'Active', '2025-08-30 18:35:47.000000', 9, 10),
(850, 'Đỏ', '2025-08-30 18:35:47.000000', 'Mẫu GUCCI - Dép Edition', 17, 'GUCCI Dép Edition', 13100000, 22, 2, 'Active', '2025-08-30 18:35:47.000000', 5, 10),
(851, 'Nâu', '2025-08-30 18:35:47.000000', 'Mẫu CHANEL - Dép Edition', 0, 'CHANEL Dép Edition', 13100000, 22, 2, 'Active', '2025-08-30 18:35:47.000000', 9, 10),
(852, 'Đỏ', '2025-08-30 18:35:47.000000', 'Mẫu GUCCI - Đồ thể thao Classic', 17, 'GUCCI Đồ thể thao Classic', 13000000, 22, 2, 'Active', '2025-08-30 18:35:47.000000', 5, 11),
(853, 'Nâu', '2025-08-30 18:35:47.000000', 'Mẫu CHANEL - Đồ thể thao Classic', 0, 'CHANEL Đồ thể thao Classic', 13000000, 22, 2, 'Active', '2025-08-30 18:35:47.000000', 9, 11),
(854, 'Đen', '2025-08-30 18:35:47.000000', 'Mẫu GUCCI - Đồ thể thao Edition', 18, 'GUCCI Đồ thể thao Edition', 13300000, 23, 3, 'Active', '2025-08-30 18:35:47.000000', 5, 11),
(855, 'Xanh', '2025-08-30 18:35:47.000000', 'Mẫu CHANEL - Đồ thể thao Edition', 1, 'CHANEL Đồ thể thao Edition', 13300000, 23, 3, 'Active', '2025-08-30 18:35:47.000000', 9, 11),
(856, 'Đen', '2025-08-30 18:35:47.000000', 'Mẫu GUCCI - Giày Classic', 18, 'GUCCI Giày Classic', 13200000, 23, 3, 'Active', '2025-08-30 18:35:47.000000', 5, 12),
(857, 'Xanh', '2025-08-30 18:35:47.000000', 'Mẫu CHANEL - Giày Classic', 1, 'CHANEL Giày Classic', 13200000, 23, 3, 'Active', '2025-08-30 18:35:47.000000', 9, 12),
(858, 'Trắng', '2025-08-30 18:35:47.000000', 'Mẫu GUCCI - Giày Edition', 19, 'GUCCI Giày Edition', 13500000, 24, 4, 'Active', '2025-08-30 18:35:47.000000', 5, 12),
(859, 'Đỏ', '2025-08-30 18:35:47.000000', 'Mẫu CHANEL - Giày Edition', 2, 'CHANEL Giày Edition', 13500000, 24, 4, 'Active', '2025-08-30 18:35:47.000000', 9, 12),
(860, 'Trắng', '2025-08-30 18:35:47.000000', 'Mẫu GUCCI - Mũ Classic', 19, 'GUCCI Mũ Classic', 13400000, 24, 4, 'Active', '2025-08-30 18:35:47.000000', 5, 13),
(861, 'Đỏ', '2025-08-30 18:35:47.000000', 'Mẫu CHANEL - Mũ Classic', 2, 'CHANEL Mũ Classic', 13400000, 24, 4, 'Active', '2025-08-30 18:35:47.000000', 9, 13),
(862, 'Be', '2025-08-30 18:35:47.000000', 'Mẫu GUCCI - Mũ Edition', 20, 'GUCCI Mũ Edition', 13700000, 10, 0, 'Active', '2025-08-30 18:35:47.000000', 5, 13),
(863, 'Đen', '2025-08-30 18:35:47.000000', 'Mẫu CHANEL - Mũ Edition', 3, 'CHANEL Mũ Edition', 13700000, 10, 0, 'Active', '2025-08-30 18:35:47.000000', 9, 13),
(864, 'Be', '2025-08-30 18:35:47.000000', 'Mẫu GUCCI - Quần jean Classic', 20, 'GUCCI Quần jean Classic', 13600000, 10, 0, 'Active', '2025-08-30 18:35:47.000000', 5, 14),
(865, 'Đen', '2025-08-30 18:35:47.000000', 'Mẫu CHANEL - Quần jean Classic', 3, 'CHANEL Quần jean Classic', 13600000, 10, 0, 'Active', '2025-08-30 18:35:47.000000', 9, 14),
(866, 'Nâu', '2025-08-30 18:35:47.000000', 'Mẫu GUCCI - Quần jean Edition', 0, 'GUCCI Quần jean Edition', 13900000, 11, 1, 'Active', '2025-08-30 18:35:47.000000', 5, 14),
(867, 'Trắng', '2025-08-30 18:35:47.000000', 'Mẫu CHANEL - Quần jean Edition', 4, 'CHANEL Quần jean Edition', 13900000, 11, 1, 'Active', '2025-08-30 18:35:47.000000', 9, 14),
(868, 'Nâu', '2025-08-30 18:35:47.000000', 'Mẫu GUCCI - Váy Classic', 0, 'GUCCI Váy Classic', 13800000, 11, 1, 'Active', '2025-08-30 18:35:47.000000', 5, 15),
(869, 'Trắng', '2025-08-30 18:35:47.000000', 'Mẫu CHANEL - Váy Classic', 4, 'CHANEL Váy Classic', 13800000, 11, 1, 'Active', '2025-08-30 18:35:47.000000', 9, 15),
(870, 'Xanh', '2025-08-30 18:35:47.000000', 'Mẫu GUCCI - Váy Edition', 1, 'GUCCI Váy Edition', 14100000, 12, 2, 'Active', '2025-08-30 18:35:47.000000', 5, 15),
(871, 'Be', '2025-08-30 18:35:47.000000', 'Mẫu CHANEL - Váy Edition', 5, 'CHANEL Váy Edition', 14100000, 12, 2, 'Active', '2025-08-30 18:35:47.000000', 9, 15);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `roles` enum('ADMIN','USER') DEFAULT NULL,
  `status_user` enum('Active','Closed') DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`id`, `avatar`, `create_date`, `email`, `full_name`, `gender`, `password`, `phone`, `roles`, `status_user`, `update_date`, `username`) VALUES
(1, NULL, NULL, NULL, NULL, NULL, '$2a$10$E8GfD5blD9hW0SwhM1cprefQNXRHo344vSgCGMeKK7ivHFP3APSoC', NULL, 'ADMIN', 'Active', NULL, 'admin'),
(2, NULL, '2025-08-30 13:46:52.000000', 'haxyz04@gmail.com', 'Nguyễn Đức Hà', 'BOY', '$2a$10$0cVbpxoo/D/6Re73.tkVpe2GD2vr9BOOJQsKZ7z.0T407rbxTDzB.', '0373187183', 'USER', 'Active', '2025-08-30 13:48:12.000000', 'ducha'),
(3, NULL, '2025-08-30 14:16:03.000000', NULL, NULL, NULL, '$2a$10$e0tSdMCFnXv1h6LwQ3xX..P2nY/HRAVFdq2Ku6Qa3uEuI1G1In0He', NULL, 'USER', 'Active', NULL, 'ducha123'),
(4, NULL, '2025-08-30 14:16:54.000000', 'haxyz@gmail.com', 'Nguyễn Đức Hà 123', 'BOY', '$2a$10$QC48Waqbd/p3jmHfYLvHwOeUALi831eNIbycSfwq117TkhURTzGZ6', '0373187182', 'USER', 'Active', '2025-08-30 14:31:11.000000', 'ducha1234'),
(5, 'https://lh3.googleusercontent.com/a/ACg8ocKMYCtY__G0nyYZKwCJHlMcyTkEvrXmdLnVFr77hltXMMZ4Pw=s96-c', '2025-09-18 10:22:15.000000', 'khangduy006@gmail.com', 'Nguyễn Đức Hà', NULL, NULL, NULL, 'USER', 'Active', '2025-09-22 09:34:36.000000', 'khangduy006@gmail.com'),
(6, 'https://lh3.googleusercontent.com/a/ACg8ocKFVx3aV5wtK6Xnw7mc5Np0CuIfWlmiNLhdHYfrHbjUNwubcg=s96-c', '2025-09-18 10:22:29.000000', 'nguyenduchasy.10@gmail.com', 'Vy Sy', NULL, NULL, NULL, 'USER', 'Active', '2025-09-18 10:27:58.000000', 'nguyenduchasy.10@gmail.com'),
(7, 'https://lh3.googleusercontent.com/a/ACg8ocJC2SzyuGjubRASd7UBG_Uzh_4F-CtfBPlCi8yYXPlLhV7HSg=s96-c', '2025-09-18 10:28:36.000000', '22111061137@hunre.edu.vn', 'HA NGUYEN DUC', NULL, NULL, NULL, 'USER', 'Active', '2025-09-18 11:55:46.000000', '22111061137@hunre.edu.vn'),
(8, 'https://lh3.googleusercontent.com/a/ACg8ocJFkLwrhStpXSGHq94XVWx4L2w4bXl9dgUT6wQ6qmZpsPs-eg=s96-c', '2025-09-18 10:41:06.000000', 'nguyenduchasy.5@gmail.com', 'Huy Ho', NULL, NULL, NULL, 'USER', 'Active', '2025-09-22 13:20:22.000000', 'nguyenduchasy.5@gmail.com');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `address_user`
--
ALTER TABLE `address_user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKe9lmb3rbwq0goy6h1c129lodw` (`user_id`);

--
-- Chỉ mục cho bảng `brand_product`
--
ALTER TABLE `brand_product`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK3d704slv66tw6x5hmbm6p2x3u` (`product_id`),
  ADD KEY `FKl70asp4l4w0jmbm1tqyofho4o` (`user_id`);

--
-- Chỉ mục cho bảng `category_product`
--
ALTER TABLE `category_product`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKm1rmnfcvq5mk26li4lit88pc5` (`product_id`),
  ADD KEY `FK8kcum44fvpupyw6f5baccx25c` (`user_id`);

--
-- Chỉ mục cho bảng `conversations`
--
ALTER TABLE `conversations`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `image_product`
--
ALTER TABLE `image_product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKml4177k7ufupebm7e4wgmvpnj` (`product_id`);

--
-- Chỉ mục cho bảng `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_msg_conv` (`conversation_id`);

--
-- Chỉ mục cho bảng `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKel9kyl84ego2otj2accfd8mr7` (`user_id`);

--
-- Chỉ mục cho bảng `order_detail`
--
ALTER TABLE `order_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKrws2q0si6oyd6il8gqe2aennc` (`order_id`),
  ADD KEY `FKb8bg2bkty0oksa3wiq5mp5qnc` (`product_id`);

--
-- Chỉ mục cho bảng `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKftbdwj61bk21210w14v2l2bgw` (`brand_id`),
  ADD KEY `FKost67owd3gqs8mmqg971nx189` (`category_id`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `address_user`
--
ALTER TABLE `address_user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `brand_product`
--
ALTER TABLE `brand_product`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT cho bảng `cart`
--
ALTER TABLE `cart`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT cho bảng `category_product`
--
ALTER TABLE `category_product`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT cho bảng `comment`
--
ALTER TABLE `comment`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `conversations`
--
ALTER TABLE `conversations`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `image_product`
--
ALTER TABLE `image_product`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=72;

--
-- AUTO_INCREMENT cho bảng `messages`
--
ALTER TABLE `messages`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `orders`
--
ALTER TABLE `orders`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

--
-- AUTO_INCREMENT cho bảng `order_detail`
--
ALTER TABLE `order_detail`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT cho bảng `product`
--
ALTER TABLE `product`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=872;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `address_user`
--
ALTER TABLE `address_user`
  ADD CONSTRAINT `FKe9lmb3rbwq0goy6h1c129lodw` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Các ràng buộc cho bảng `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `FK3d704slv66tw6x5hmbm6p2x3u` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `FKl70asp4l4w0jmbm1tqyofho4o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Các ràng buộc cho bảng `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `FK8kcum44fvpupyw6f5baccx25c` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKm1rmnfcvq5mk26li4lit88pc5` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

--
-- Các ràng buộc cho bảng `image_product`
--
ALTER TABLE `image_product`
  ADD CONSTRAINT `FKml4177k7ufupebm7e4wgmvpnj` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

--
-- Các ràng buộc cho bảng `messages`
--
ALTER TABLE `messages`
  ADD CONSTRAINT `fk_msg_conv` FOREIGN KEY (`conversation_id`) REFERENCES `conversations` (`id`);

--
-- Các ràng buộc cho bảng `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `FKel9kyl84ego2otj2accfd8mr7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Các ràng buộc cho bảng `order_detail`
--
ALTER TABLE `order_detail`
  ADD CONSTRAINT `FKb8bg2bkty0oksa3wiq5mp5qnc` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `FKrws2q0si6oyd6il8gqe2aennc` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

--
-- Các ràng buộc cho bảng `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `FKftbdwj61bk21210w14v2l2bgw` FOREIGN KEY (`brand_id`) REFERENCES `brand_product` (`id`),
  ADD CONSTRAINT `FKost67owd3gqs8mmqg971nx189` FOREIGN KEY (`category_id`) REFERENCES `category_product` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
