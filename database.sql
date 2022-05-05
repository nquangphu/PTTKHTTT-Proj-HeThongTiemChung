﻿--USE MASTER; DROP DATABASE HTTC
CREATE DATABASE HTTC
GO
USE HTTC
GO
CREATE TABLE NHA_CUNG_CAP (
    MaNCC INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    DiaChi NVARCHAR(100) NOT NULL,
    Email VARCHAR(100),
    SDT CHAR(12) NOT NULL
);
GO
CREATE TABLE LOAI_VAC_XIN (
    MaLoaiVX INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
	TenLoai NVARCHAR(100) NOT NULL,
	TonKho INT NOT NULL DEFAULT 0 CHECK (TonKho >= 0),
	GiaBan DECIMAL(19, 4) NOT NULL CHECK (GiaBan >= 0),
    MoTa NVARCHAR(200)
);
INSERT INTO LOAI_VAC_XIN (TenLoai, TonKho, GiaBan, MoTa) VALUES
(N'Vắc xin viêm gan A', 20, 200000, N'Dùng để phòng viêm gan A'),
(N'Vắc xin Vero cell', 20, 200000, N'Dùng để phòng chống covid-19'),
(N'Vắc xin Astrazeneca', 30, 400000, N'Dùng để phòng chống covid-19'),
(N'Vắc xin viêm não Nhật Bản', 10, 150000, N'Dùng để phòng viêm não Nhật Bản'),
(N'Vắc xin dại', 50, 300000, N'Dùng để phòng bệnh dại'),
(N'Vắc xin tay chân miệng', 20, 500000, N'Dùng để phòng tay chân miệng ở trẻ'),
(N'Vắc xin thủy đậu', 20, 200000, N'Dùng để phòng thủy đậu'),
(N'Vắc xin uốn ván', 10, 200000, N'Dùng để phòng bệnh uốn ván'),
(N'Vắc xin viêm gan B', 20, 200000, N'Dùng để phòng viêm gan B'),
(N'Vắc xin đắt', 20, 10000000, N'Dùng để phòng mọi bệnh');

GO
CREATE TABLE KHACH_HANG (
    MaKH INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
	HoTen NVARCHAR(100) NOT NULL,
	NgaySinh DATE NOT NULL,
	GioiTinh CHAR(1) NOT NULL,
    DiaChi NVARCHAR(100) NOT NULL,
    SDT VARCHAR(12) NOT NULL,
	Username VARCHAR(128) NOT NULL UNIQUE,
    Email VARCHAR(100)
);

GO
CREATE TABLE NGUOI_GIAM_HO (
    HoTen NVARCHAR(100) NOT NULL,
    QuanHe NVARCHAR(30),
	MaKH INT NOT NULL PRIMARY KEY,
	CONSTRAINT NGH_FK_TO_KH FOREIGN KEY (MaKH) REFERENCES KHACH_HANG(MaKH),
	SDT VARCHAR(12) NOT NULL
);
GO
CREATE TABLE THE_KHACH_HANG (
    MaThe INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
	MaKH INT NOT NULL,
    NgayCap DATE NOT NULL DEFAULT GETDATE(),
    HSD DATE NOT NULL,
    CONSTRAINT FK_THE_KHACH_HANG_TO_KHACH_HANG FOREIGN KEY (MaKH) REFERENCES KHACH_HANG(MaKH)
);
GO
CREATE TABLE VAC_XIN (
    MaVX INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    NgayNhap DATE NOT NULL DEFAULT GETDATE(),
	HSD DATE NOT NULL,
	MaLoaiVX INT NOT NULL,
	TrangThai NVARCHAR(30) NOT NULL DEFAULT N'Khả dụng',
    CONSTRAINT FK_VAC_XIN_TO_LOAI_VAC_XIN FOREIGN KEY (MaLoaiVX) REFERENCES LOAI_VAC_XIN(MaLoaiVX)
);
GO
CREATE TABLE CHI_TIET_CUNG_CAP (
	MaNCC INT NOT NULL,
	MaLoaiVX INT NOT NULL,
	GiaCC DECIMAL(19, 4) NOT NULL CHECK (GiaCC >=0),
	PRIMARY KEY (MaNCC, MaLoaiVX),
	CONSTRAINT FK_CT_CUNG_CAP_TO_VAC_XIN FOREIGN KEY (MaLoaiVX) REFERENCES LOAI_VAC_XIN(MaLoaiVX),
	CONSTRAINT FK_CT_CUNG_CAP_TO_NCC FOREIGN KEY (MaNCC) REFERENCES NHA_CUNG_CAP(MaNCC)
);
GO
CREATE TABLE GOI_TIEM_CHUNG (
    MaGoi INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
	TenGoi NVARCHAR(50) NOT NULL,
    GiaGoi INT NOT NULL CHECK (GiaGoi >= 0),
	TonKho INT NOT NULL DEFAULT 0 CHECK (TonKho >= 0),
    MoTa NVARCHAR(200)
);
INSERT INTO GOI_TIEM_CHUNG (TenGoi, GiaGoi, TonKho, MoTa) VALUES
(N'Gói cho trẻ', 200000, 20, N'Gói vắc xin toàn diện cho trẻ em'),
(N'Gói toàn diện', 150000, 10, N'Gói toàn diện cho người từ 15 tuổi'),
(N'Gói covid-19', 200000, 27, N'Gói dùng phòng covid-19 (3 mũi)');

GO
CREATE TABLE CT_GOI_TIEM_CHUNG (
    MaGoi INT NOT NULL,
    MaLoaiVX INT NOT NULL,
	SoLuong INT NOT NULL CHECK (SoLuong >= 0),
    PRIMARY KEY (MaGoi, MaLoaiVX),
    CONSTRAINT FK_CT_GOI_TIEM_CHUNG_TO_GOI_TIEM_CHUNG FOREIGN KEY (MaGoi) REFERENCES GOI_TIEM_CHUNG(MaGoi),
    CONSTRAINT FK_CT_GOI_TIEM_CHUNG_TO_VAC_XIN FOREIGN KEY (MaLoaiVX) REFERENCES LOAI_VAC_XIN(MaLoaiVX)
);
INSERT INTO CT_GOI_TIEM_CHUNG (MaGoi, MaLoaiVX, SoLuong) VALUES
(1, 2, 1),
(1, 4, 1),
(1, 5, 1),
(2, 2, 1),
(2, 3, 1),
(2, 5, 1),
(2, 6, 1),
(3, 7, 2),
(3, 8, 1);
GO
SELECT * FROM LOAI_VAC_XIN;
SELECT * FROM GOI_TIEM_CHUNG;
SELECT * FROM CT_GOI_TIEM_CHUNG;
GO
CREATE TABLE TRUNG_TAM (
    MaTT INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
	TenTT NVARCHAR(100) NOT NULL,
    DiaChi NVARCHAR(100) NOT NULL,
	NgayLap DATE NOT NULL DEFAULT GETDATE()
);
GO
CREATE TABLE NHAN_VIEN (
    MaNV INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
	HoTen NVARCHAR(100) NOT NULL,
	NgaySinh DATE NOT NULL,
    DiaChi NVARCHAR(100) NOT NULL,
    SDT CHAR(12) NOT NULL,
    Email VARCHAR(100) NOT NULL,
	Luong DECIMAL(19,4) NOT NULL CHECK (Luong >= 0),
    VaiTro NVARCHAR(30),
    BangCap NVARCHAR(30),
	Username VARCHAR(128) NOT NULL UNIQUE,
	MaTT INT NOT NULL,
	FOREIGN KEY (MaTT) REFERENCES TRUNG_TAM(MaTT)
);

GO
CREATE TABLE LICH_LAM_VIEC (
    MaLLV INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
	MaTT INT NOT NULL,
	NgayLV DATE NOT NULL,
    Ca NVARCHAR(10) NOT NULL,
    SLNV INT NOT NULL CHECK (SLNV >= 0),
	CONSTRAINT UNQ_LLV UNIQUE(MaTT, NgayLV, Ca),
    CONSTRAINT FK_LICH_LAM_VIEC_TO_TRUNG_TAM FOREIGN KEY (MaTT) REFERENCES TRUNG_TAM(MaTT),
);
CREATE TABLE PHAN_CONG (
	MaNV INT NOT NULL,
	MaLLV INT NOT NULL,
	PRIMARY KEY (MaLLV, MaNV),
	CONSTRAINT FK_PHAN_CONG_TO_NHAN_VIEN FOREIGN KEY (MaNV) REFERENCES NHAN_VIEN(MaNV),
	CONSTRAINT FK_PHAN_CONG_TO_LLV FOREIGN KEY (MaLLV) REFERENCES LICH_LAM_VIEC(MaLLV)
);
GO
CREATE TABLE THE_ATM (
	NganHang	VARCHAR(50) NOT NULL,
	ChuThe		VARCHAR(100) NOT NULL,
	SoThe		VARCHAR(20) NOT NULL,
	PRIMARY KEY (NganHang, SoThe)
);
GO
CREATE TABLE PHIEU_DKTC (
    MaPDKTC INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
	MaKH INT,
    NgayLap DATE NOT NULL DEFAULT GETDATE(),
	MaNV INT,
    MaTT INT,
	TongGia DECIMAL(19, 4) NOT NULL DEFAULT 0 CHECK (TongGia >= 0),
	TrangThai NVARCHAR(30) NOT NULL DEFAULT N'Chưa duyệt',
    CONSTRAINT FK_PHIEU_DKTC_TO_KHACH_HANG FOREIGN KEY (MaKH) REFERENCES KHACH_HANG(MaKH),
    CONSTRAINT FK_PHIEU_DKTC_TO_TRUNG_TAM FOREIGN KEY (MaTT) REFERENCES TRUNG_TAM(MaTT),
    CONSTRAINT FK_PHIEU_DKTC_TO_NHAN_VIEN FOREIGN KEY (MaNV) REFERENCES NHAN_VIEN(MaNV)
);

GO
CREATE TABLE PHIEU_DKTC_KHONG_DK (
	MaPDKTC INT NOT NULL PRIMARY KEY,
	HoTen NVARCHAR(100) NOT NULL,
	NgaySinh DATE NOT NULL,
	GioiTinh CHAR(1) NOT NULL,
    DiaChi NVARCHAR(100) NOT NULL,
    SDT VARCHAR(12) NOT NULL,
    Email VARCHAR(100) NOT NULL,
	HoTenNGH NVARCHAR(100),
    QuanHeNGH NVARCHAR(30),
	SDTNGH VARCHAR(12),
	CONSTRAINT FK_PHIEU_DKTC_KDK_TO_PDKTC FOREIGN KEY (MaPDKTC) REFERENCES PHIEU_DKTC(MaPDKTC)
);
GO
CREATE TABLE CT_PHIEU_DKTC_LE (
    MaPDKTC INT NOT NULL,
	MaLoaiVX INT NOT NULL,
	PRIMARY KEY (MAPDKTC, MaLoaiVX),
	DonGia DECIMAL(19, 4) NOT NULL CHECK (DonGia > 0),
	NgayMuonTiem DATE,
    CONSTRAINT FK_CT_PHIEU_DKTC_LE_TO_PHIEU_DKTC FOREIGN KEY (MaPDKTC) REFERENCES PHIEU_DKTC(MaPDKTC),
    CONSTRAINT FK_CT_PHIEU_DKTC_TO_VAC_XIN FOREIGN KEY (MaLoaiVX) REFERENCES LOAI_VAC_XIN(MaLoaiVX)
);
GO
CREATE TABLE CT_PHIEU_DKTC_GOI (
    MaPDKTC INT NOT NULL,
	MaGoi INT NOT NULL,
	PRIMARY KEY (MAPDKTC, MaGoi),
	DonGia DECIMAL(19, 4) NOT NULL CHECK (DonGia > 0),
	NgayMuonTiem DATE,
    CONSTRAINT FK_CT_PHIEU_DKTC_GOI_TO_PHIEU_DKTC FOREIGN KEY (MaPDKTC) REFERENCES PHIEU_DKTC(MaPDKTC),
    CONSTRAINT FK_CT_PHIEU_DKTC_TO_GOI_TIEM_CHUNG FOREIGN KEY (MaGoi) REFERENCES GOI_TIEM_CHUNG(MaGoi)
);
GO
CREATE TABLE HSTC (
    MaHS INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    NgayLap DATE NOT NULL DEFAULT GETDATE(),
    MaTT INT NOT NULL,
    MaKH INT NOT NULL,
	MaNV INT NOT NULL,
    CONSTRAINT FK_HSTC_TO_KHACH_HANG FOREIGN KEY (MaKH) REFERENCES KHACH_HANG(MaKH),
    CONSTRAINT FK_HSTC_TO_TRUNG_TAM FOREIGN KEY (MaTT) REFERENCES TRUNG_TAM(MaTT),
	CONSTRAINT FK_HSTC_TO_NV FOREIGN KEY (MaNV) REFERENCES NHAN_VIEN(MaNV)
);
GO
CREATE TABLE KET_QUA_TIEM (
	MaHS INT NOT NULL,
	MaTT INT NOT NULL,
	NgayTiem DATE NOT NULL,
	MaVX INT NOT NULL,
	MaPDKTC INT NOT NULL,
	MaLoaiVX INT,
	MaGoi INT,
	PRIMARY KEY (MaHS, MaVX),
    KQSangLoc NVARCHAR(100),
    KQTiem NVARCHAR(100),
    NXBacSi NVARCHAR(100),
    CONSTRAINT FK_KET_QUA_TIEM_TO_HSTC FOREIGN KEY (MaHS) REFERENCES HSTC(MaHS),
	CONSTRAINT FK_KET_QUA_TIEM_TO_VX FOREIGN KEY (MaVX) REFERENCES VAC_XIN(MaVX),
	CONSTRAINT FK_KQT_TO_TRUNG_TAM FOREIGN KEY (MaTT) REFERENCES TRUNG_TAM(MaTT),
    CONSTRAINT FK_KET_QUA_TIEM_TO_PDKTC_LE FOREIGN KEY (MaPDKTC, MaLoaiVX) REFERENCES CT_PHIEU_DKTC_LE(MaPDKTC, MaLoaiVX),
	CONSTRAINT FK_KET_QUA_TIEM_TO_PDKTC_GOI FOREIGN KEY (MaPDKTC, MaGoi) REFERENCES CT_PHIEU_DKTC_Goi(MaPDKTC, MaGoi)
);
GO
CREATE TABLE HOA_DON 
(
    MaHD INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    NgayLap DATE NOT NULL DEFAULT GETDATE(),
	MaTT INT,
	MaNV INT,
	NganHang VARCHAR(50),
	SoThe	 VARCHAR(20),
	TongTien DECIMAL(19, 4) NOT NULL CHECK (TongTien >= 0),
	TrangThai NVARCHAR(30) NOT NULL DEFAULT N'Chưa thanh toán',
	MaPDKTC INT NOT NULL,
	UNIQUE (MaPDKTC),
    CONSTRAINT FK_HOA_DON_TO_PDKTC FOREIGN KEY (MaPDKTC) REFERENCES PHIEU_DKTC(MaPDKTC),
    CONSTRAINT FK_HOA_DON_TO_TRUNG_TAM FOREIGN KEY (MaTT) REFERENCES TRUNG_TAM(MaTT),
    CONSTRAINT FK_HOA_DON_TO_NHAN_VIEN FOREIGN KEY (MaNV) REFERENCES NHAN_VIEN(MaNV),
	CONSTRAINT FK_HD_TO_THE_ATM FOREIGN KEY (NganHang, SoThe) REFERENCES THE_ATM(NganHang, SoThe)
);
GO
CREATE TABLE DOT_THANH_TOAN
(
	MaHD INT NOT NULL,
	NgayBD DATE NOT NULL,
	NgayKT DATE NOT NULL,
	SoTien DECIMAL(19,4) NOT NULL CHECK (SoTien >= 0),
	TrangThai NVARCHAR(30) NOT NULL DEFAULT N'Chưa thanh toán',
	NganHang VARCHAR(50),
	SoThe	 VARCHAR(20),
	PRIMARY KEY (MaHD, NgayBD),
	CONSTRAINT FK_DOT_TT_TO_HOA_DON FOREIGN KEY (MaHD) REFERENCES HOA_DON(MaHD),
	CONSTRAINT FK_DTT_TO_THE_ATM FOREIGN KEY (NganHang, SoThe) REFERENCES THE_ATM(NganHang, SoThe)
);
GO
CREATE TABLE PHIEU_DMVX (
    MaPDMVX INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
	MaKH INT,
	MaNV INT,
	MaTT INT,
	MaPDKTC INT,
    NgayLap DATE NOT NULL DEFAULT GETDATE(),
    TrangThai NVARCHAR(30) NOT NULL DEFAULT N'Chưa đặt',
	CONSTRAINT FK_PHIEU_DMVX_TO_PDKTC FOREIGN KEY (MaPDKTC) REFERENCES PHIEU_DKTC(MaPDKTC),
    CONSTRAINT FK_PHIEU_DMVX_TO_KHACH_HANG FOREIGN KEY (MaKH) REFERENCES KHACH_HANG(MaKH),
    CONSTRAINT FK_PHIEU_DMVX_TO_TRUNG_TAM_FK FOREIGN KEY (MaTT) REFERENCES TRUNG_TAM(MaTT),
    CONSTRAINT FK_PHIEU_DMVX_TO_NHAN_VIEN FOREIGN KEY (MaNV) REFERENCES NHAN_VIEN(MaNV)
);
GO
CREATE TABLE PHIEU_DMVX_KHONG_DK (
    MaPDMVX INT NOT NULL PRIMARY KEY,
	HoTen NVARCHAR(100) NOT NULL,
	NgaySinh DATE NOT NULL,
	GioiTinh CHAR(1) NOT NULL,
    DiaChi NVARCHAR(100) NOT NULL,
    SDT VARCHAR(12) NOT NULL,
    Email VARCHAR(100) NOT NULL,
	HoTenNGH NVARCHAR(100),
    QuanHeNGH NVARCHAR(30),
	SDTNGH VARCHAR(12),
	CONSTRAINT FK_PHIEU_DMVX_KDK_TO_PHIEU_DMVX FOREIGN KEY (MaPDMVX) REFERENCES PHIEU_DMVX(MaPDMVX)
);
GO
CREATE TABLE CT_PHIEU_DMVX_GOI (
    MaPDMVX INT NOT NULL,
	MaGoi INT NOT NULL,
	PRIMARY KEY (MaPDMVX, MaGoi),
    CONSTRAINT FK_CT_PHIEU_DMVX_GOI_TO_PHIEU_DMVX FOREIGN KEY (MaPDMVX) REFERENCES PHIEU_DMVX(MaPDMVX),
    CONSTRAINT FK_CT_PHIEU_DMVX_TO_GOI_TIEM_CHUNG FOREIGN KEY (MaGoi) REFERENCES GOI_TIEM_CHUNG(MaGoi)
);
GO
CREATE TABLE CT_PHIEU_DMVX_LE (
    MaPDMVX INT NOT NULL,
	MaLoaiVX INT NOT NULL,
	PRIMARY KEY (MaPDMVX, MaLoaiVX),
    CONSTRAINT FK_CT_PHIEU_DMVX_LE_TO_PHIEU_DMVX FOREIGN KEY (MaPDMVX) REFERENCES PHIEU_DMVX(MaPDMVX),
    CONSTRAINT FK_CT_PHIEU_DMVX_TO_VAC_XIN FOREIGN KEY (MaLoaiVX) REFERENCES LOAI_VAC_XIN(MaLoaiVX)
);
GO
CREATE TABLE CT_PHIEU_DMVX_KHONG_TRONG_HE_THONG (
    MaPDMVX INT NOT NULL,
	TenLoaiVX NVARCHAR(100) NOT NULL,
	NhaSX NVARCHAR(100),
	Gia DECIMAL(19, 4)
	PRIMARY KEY (MaPDMVX, TenLoaiVX),
    CONSTRAINT FK_CT_PHIEU_DMVX_KHONG_CO_TO_PHIEU_DMVX FOREIGN KEY (MaPDMVX) REFERENCES PHIEU_DMVX(MaPDMVX)
);
GO
CREATE TABLE PHIEU_DAT_HANG (
    MaPDH INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
	MaPDMVX INT NOT NULL,
    NgayLap DATE NOT NULL DEFAULT GETDATE(),
	MaNV INT,
	MaTT INT,
    TrangThai NVARCHAR(30) NOT NULL DEFAULT N'Chưa chọn nhà cung cấp',
	CONSTRAINT FK_PHIEU_DAT_HANG_TO_PHIEU_DMVX FOREIGN KEY (MaPDMVX) REFERENCES PHIEU_DMVX(MaPDMVX),
	CONSTRAINT FK_PHIEU_DH_TO_TRUNG_TAM FOREIGN KEY (MaTT) REFERENCES TRUNG_TAM(MaTT),
    CONSTRAINT FK_PHIEU_DH_TO_NHAN_VIEN FOREIGN KEY (MaNV) REFERENCES NHAN_VIEN(MaNV)
);
GO
CREATE TABLE CT_PHIEU_DAT_HANG (
    MaPDH INT NOT NULL,
	MaLoaiVX INT NOT NULL,
	MaNCC INT NOT NULL,
	SoLuong INT NOT NULL CHECK (SoLuong > 0),
	GiaBan DECIMAL(19, 4) NOT NULL CHECK (GiaBan > 0),
    CONSTRAINT FK_CT_PDH_TO_PDH FOREIGN KEY (MaPDH) REFERENCES PHIEU_DAT_HANG(MaPDH),
    CONSTRAINT FK_CT_PDH_TO_VAC_XIN FOREIGN KEY (MaLoaiVX) REFERENCES LOAI_VAC_XIN(MaLoaiVX),
	CONSTRAINT FK_CT_PDH_TO_NHA_CUNG_CAP FOREIGN KEY (MaNCC) REFERENCES NHA_CUNG_CAP(MaNCC)
);
GO

CREATE TYPE ID_DATE AS
TABLE (
	id INT,
	date DATE
);
GO

CREATE OR ALTER PROC tao_ct_pdktc @ma_pdktc INT, @gtc ID_DATE READONLY, @vx ID_DATE READONLY
AS
BEGIN TRANSACTION
	BEGIN TRY
		DECLARE @tong_gia DECIMAL(19, 4), @tong_gia_goi DECIMAL(19, 4), @tong_gia_le DECIMAL(19, 4);
		
		INSERT INTO CT_PHIEU_DKTC_GOI  (MaPDKTC, MaGoi, DonGia, NgayMuonTiem)
		SELECT @ma_pdktc, g.id, gtc.GiaGoi, g.date
		FROM @gtc g
			JOIN GOI_TIEM_CHUNG gtc ON g.id = gtc.MaGoi;

		INSERT INTO CT_PHIEU_DKTC_LE (MaPDKTC, MaLoaiVX, DonGia, NgayMuonTiem)
		SELECT @ma_pdktc, g.id, lvx.GiaBan, g.date
		FROM @vx g
			JOIN LOAI_VAC_XIN lvx ON g.id = lvx.MaLoaiVX;

		UPDATE LOAI_VAC_XIN
		SET TonKho -= 1
		WHERE MaLoaiVX IN (SELECT v.id FROM @vx v);


		DECLARE GoiCursor CURSOR LOCAL STATIC READ_ONLY FORWARD_ONLY
			FOR SELECT id FROM @gtc;
		DECLARE @gtc_id INT;
		OPEN GoiCursor;
		FETCH NEXT FROM GoiCursor INTO @gtc_id;
		WHILE @@FETCH_STATUS = 0
		BEGIN
			UPDATE LOAI_VAC_XIN
			SET TonKho -= (SELECT TOP 1 ct.SoLuong FROM CT_GOI_TIEM_CHUNG ct WHERE ct.MaLoaiVX = MaLoaiVX AND ct.MaGoi = @gtc_id)
			WHERE MaLoaiVX IN (SELECT ct.MaLoaiVX FROM CT_GOI_TIEM_CHUNG ct WHERE ct.MaLoaiVX = MaLoaiVX AND ct.MaGoi = @gtc_id);
			
			FETCH NEXT FROM GoiCursor INTO @gtc_id;
		END
		CLOSE GoiCursor;
		DEALLOCATE GoiCursor;

		SET @tong_gia = 0;
		SET @tong_gia_goi = (SELECT SUM(DonGia) FROM CT_PHIEU_DKTC_GOI WHERE MaPDKTC = @ma_pdktc);
		SET @tong_gia_le = (SELECT SUM(DonGia) FROM CT_PHIEU_DKTC_LE WHERE MaPDKTC = @ma_pdktc);
		IF @tong_gia_goi IS NOT NULL
		BEGIN
			SET @tong_gia += @tong_gia_goi;
		END;
		IF @tong_gia_le IS NOT NULL
		BEGIN
			SET @tong_gia += @tong_gia_le;
		END;
		UPDATE PHIEU_DKTC
		SET TongGia = @tong_gia
		WHERE MaPDKTC = @ma_pdktc;
		COMMIT;
	END TRY
	BEGIN CATCH
		ROLLBACK;
		THROW;
	END CATCH;

GO
CREATE OR ALTER PROC tao_pdktc @ma_kh INT, @gtc ID_DATE READONLY, @vx ID_DATE READONLY, @ma_pdktc INT OUT
AS
BEGIN TRANSACTION
	BEGIN TRY
		INSERT INTO PHIEU_DKTC (MaKH)
		VALUES (@ma_kh);
		SET @ma_pdktc = (SELECT MAX(MaPDKTC) FROM PHIEU_DKTC);
		EXEC tao_ct_pdktc @ma_pdktc, @gtc, @vx;
		COMMIT;
	END TRY
	BEGIN CATCH
		ROLLBACK;
		THROW;
	END CATCH;
GO
CREATE OR ALTER PROC tao_pdktc_khong_dk @ho_ten NVARCHAR(100), @ngay_sinh DATE, @gioi_tinh CHAR(1), @dia_chi NVARCHAR(100),
				@sdt VARCHAR(12), @email VARCHAR(100), @ho_ten_ngh NVARCHAR(100),
				@quan_he NVARCHAR(30), @sdt_ngh VARCHAR(12),
				@gtc ID_DATE READONLY, @vx ID_DATE READONLY, @ma_pdktc INT OUT
AS
BEGIN TRANSACTION
	BEGIN TRY
		INSERT INTO PHIEU_DKTC(NgayLap)
		VALUES (GETDATE());
		SET @ma_pdktc = (SELECT MAX(MaPDKTC) FROM PHIEU_DKTC);

		INSERT INTO PHIEU_DKTC_KHONG_DK (MaPDKTC, HoTen, NgaySinh, GioiTinh, DiaChi, SDT, Email,
					HoTenNGH, QuanHeNGH, SDTNGH)
		VALUES (@ma_pdktc, @ho_ten, @ngay_sinh, @gioi_tinh, @dia_chi, @sdt, @email,
				@ho_ten_ngh, @quan_he, @sdt_ngh);

		EXEC tao_ct_pdktc @ma_pdktc, @gtc, @vx;
		COMMIT;
	END TRY
	BEGIN CATCH
		ROLLBACK;
		THROW;
	END CATCH;
GO
CREATE OR ALTER PROC tao_hd_toan_bo @ma_pdktc INT, @tong_tien DECIMAL, @ng_hang VARCHAR(50), @so_the VARCHAR(20), @chu_the VARCHAR(100), @ma_hd INT OUT
AS
BEGIN TRANSACTION
	BEGIN TRY
		IF NOT EXISTS (SELECT atm.SoThe
						FROM THE_ATM atm
						WHERE atm.NganHang = @ng_hang AND atm.SoThe = @so_the)
			BEGIN
				INSERT INTO THE_ATM(NganHang, SoThe, ChuThe)
				VALUES (@ng_hang, @so_the, @chu_the);
			END;
		INSERT INTO HOA_DON(MaPDKTC, TongTien, NganHang, SoThe, TrangThai)
		VALUES (@ma_pdktc, @tong_tien, @ng_hang, @so_the, N'Đã thanh toán');

		SET @ma_hd = (SELECT MAX(hd.MaHD) FROM HOA_DON hd);

		UPDATE PHIEU_DKTC
		SET TrangThai = N'Đã thanh toán'
		WHERE MaPDKTC = @ma_pdktc;

		COMMIT;
	END TRY
	BEGIN CATCH
		ROLLBACK;
		THROW;
	END CATCH;
GO
CREATE TYPE tao_dot_thanh_toan_type AS TABLE
(
	NgayBD DATE,
	NgayKT DATE,
	SoTien DECIMAL(19, 4)
);
GO
CREATE OR ALTER PROC tao_hd_theo_dot @ma_pdktc INT, @tong_tien DECIMAL, @dot_thanh_toan tao_dot_thanh_toan_type READONLY, @ma_hd INT OUT
AS
BEGIN TRANSACTION
	BEGIN TRY
		INSERT INTO HOA_DON(MaPDKTC, TongTien, TrangThai)
		VALUES (@ma_pdktc, @tong_tien, N'Đang thanh toán theo đợt');
		SET @ma_hd = (SELECT MAX(hd.MaHD) FROM HOA_DON hd);

		INSERT INTO DOT_THANH_TOAN(MaHD, NgayBD, NgayKT, SoTien)
		SELECT @ma_hd, dtt.NgayBD, dtt.NgayKT, dtt.SoTien
		FROM @dot_thanh_toan dtt;
		COMMIT;
	END TRY
	BEGIN CATCH
		ROLLBACK;
		THROW;
	END CATCH;
GO
CREATE OR ALTER PROC thanh_toan_dot @ma_hd INT, @ngay_bd DATE, @ng_hang VARCHAR(50), @so_the VARCHAR(20), @chu_the VARCHAR(100)
AS
BEGIN TRANSACTION
	BEGIN TRY
		IF NOT EXISTS (SELECT atm.SoThe
						FROM THE_ATM atm
						WHERE atm.NganHang = @ng_hang AND atm.SoThe = @so_the)
			BEGIN
				INSERT INTO THE_ATM(NganHang, SoThe, ChuThe)
				VALUES (@ng_hang, @so_the, @chu_the);
			END;
		UPDATE DOT_THANH_TOAN
		SET NganHang = @ng_hang, SoThe = @so_the, TrangThai = N'Đã thanh toán'
		WHERE MaHD = @ma_hd AND NgayBD = @ngay_bd;

		IF (SELECT COUNT(dtt.MaHD)
			FROM DOT_THANH_TOAN dtt
			WHERE dtt.MaHD = @ma_hd AND dtt.TrangThai != N'Đã thanh toán') = 0
		BEGIN
			UPDATE HOA_DON
			SET TrangThai = N'Đã thanh toán'
			WHERE MaHD = @ma_hd;

			UPDATE PHIEU_DKTC
			SET TrangThai = N'Đã thanh toán'
			WHERE MaPDKTC = (SELECT hd.MaPDKTC FROM HOA_DON hd WHERE hd.MaHD = @ma_hd);
		END;
		COMMIT;
	END TRY
	BEGIN CATCH
		ROLLBACK;
		THROW;
	END CATCH;
GO

CREATE TYPE IDS AS TABLE (
	ID INT
);
GO
CREATE TYPE LVX_KHONG_HT AS TABLE (
	TenLoaiVX NVARCHAR(100),
	NhaSX NVARCHAR(100),
	Gia   DECIMAL(19,4)
);
GO
CREATE OR ALTER PROC tao_ct_pdmvx @ma_pdmvx INT, @gtc IDS READONLY, @vx IDS READONLY, @vxn LVX_KHONG_HT READONLY
AS
BEGIN TRANSACTION
	BEGIN TRY
		INSERT INTO CT_PHIEU_DMVX_GOI  (MaPDMVX, MaGoi)
		SELECT @ma_pdmvx, g.ID
		FROM @gtc g;

		INSERT INTO CT_PHIEU_DMVX_LE (MaPDMVX, MaLoaiVX)
		SELECT @ma_pdmvx, g.ID
		FROM @vx g;

		INSERT INTO CT_PHIEU_DMVX_KHONG_TRONG_HE_THONG (MaPDMVX, TenLoaiVX, NhaSX, Gia)
		SELECT @ma_pdmvx, g.TenLoaiVX, g.NhaSX, g.Gia
		FROM @vxn g;
		COMMIT;
	END TRY
	BEGIN CATCH
		ROLLBACK;
		THROW;
	END CATCH;
GO
CREATE OR ALTER PROC tao_pdmvx @ma_kh INT, @gtc IDS READONLY, @vx IDS READONLY, @vxn LVX_KHONG_HT READONLY, @ma_pdmvx INT OUT
AS
BEGIN TRANSACTION
	BEGIN TRY
		INSERT INTO PHIEU_DMVX(MaKH)
		VALUES (@ma_kh);
		SET @ma_pdmvx = (SELECT MAX(MaPDMVX) FROM PHIEU_DMVX);
		EXEC tao_ct_pdmvx @ma_pdmvx, @gtc, @vx, @vxn;
		COMMIT;
	END TRY
	BEGIN CATCH
		ROLLBACK;
		THROW;
	END CATCH;
GO
CREATE OR ALTER PROC tao_pdmvx_khong_dk @ho_ten NVARCHAR(100), @ngay_sinh DATE, @gioi_tinh CHAR(1), @dia_chi NVARCHAR(100),
				@sdt VARCHAR(12), @email VARCHAR(100), @ho_ten_ngh NVARCHAR(100),
				@quan_he NVARCHAR(30), @sdt_ngh VARCHAR(12),
				@gtc IDS READONLY, @vx IDS READONLY, @vxn LVX_KHONG_HT READONLY,
				@ma_pdmvx INT OUT
AS
BEGIN TRANSACTION
	BEGIN TRY
		INSERT INTO PHIEU_DMVX(NgayLap)
		VALUES (GETDATE());
		SET @ma_pdmvx = (SELECT MAX(MaPDMVX) FROM PHIEU_DMVX);
		INSERT INTO PHIEU_DMVX_KHONG_DK (MaPDMVX, HoTen, NgaySinh, GioiTinh, DiaChi, SDT, Email,
					HoTenNGH, QuanHeNGH, SDTNGH)
		VALUES (@ma_pdmvx, @ho_ten, @ngay_sinh, @gioi_tinh, @dia_chi, @sdt, @email,
				@ho_ten_ngh, @quan_he, @sdt_ngh);
		EXEC tao_ct_pdmvx @ma_pdmvx, @gtc, @vx, @vxn;
		COMMIT;
	END TRY
	BEGIN CATCH
		ROLLBACK;
		THROW;
	END CATCH;

GO
CREATE OR ALTER PROCEDURE lay_vai_tro 
AS
BEGIN
	DECLARE @ma_kh INT, @ma_nv INT, @vai_tro VARCHAR(30);
	SET @ma_kh = (SELECT kh.MaKH FROM KHACH_HANG kh WHERE SUSER_NAME() = kh.Username);
	IF @ma_kh IS NOT NULL
	BEGIN
		SELECT @ma_kh, 'KH';
	END;
	SET @ma_nv = (SELECT nv.MaNV FROM NHAN_VIEN nv WHERE SUSER_NAME() = nv.Username);
	IF @ma_nv IS NOT NULL
	BEGIN
		SELECT TOP 1 nv.MaNV, nv.VaiTro FROM NHAN_VIEN nv WHERE nv.MaNV = @ma_nv;
	END;
END;
GO
BEGIN
	CREATE ROLE khach_hang;
	CREATE ROLE nhan_vien;
	CREATE ROLE quan_ly;
	DROP LOGIN khachhang1;
	CREATE LOGIN khachhang1 WITH PASSWORD = 'kh';
	DROP USER khach_hang_1;
	CREATE USER khach_hang_1 FOR LOGIN KHACHHANG1;
	ALTER ROLE khach_hang ADD MEMBER khach_hang_1;
	DROP LOGIN NHANVIEN1;
	CREATE LOGIN nhanvien1 WITH PASSWORD = 'nv';
	DROP USER nhan_vien_1;
	CREATE USER nhan_vien_1 FOR LOGIN NHANVIEN1;
	ALTER ROLE nhan_vien ADD MEMBER nhan_vien_1;
	DROP LOGIN quanly1;
	CREATE LOGIN quanly1 WITH PASSWORD = 'ql';
	DROP USER quan_ly_1;
	CREATE USER quan_ly_1 FOR LOGIN QUANLY1;
	ALTER ROLE quan_ly ADD MEMBER quan_ly_1;
END;

INSERT INTO KHACH_HANG (HoTen, NgaySinh, GioiTinh, DiaChi, SDT, Email, Username)
VALUES (N'Hồ Linh', '2000-05-19', 'M', N'Quận 7 TPHCM', '0989991119', 'sk111@gmail.com','khachhang1');
GO
INSERT INTO TRUNG_TAM (TenTT, DiaChi, NgayLap)
VALUES (N'Trung tâm Quận 7', N'278 Huỳnh Tấn Phát Q7 TPHCM', '2002-05-13'); 
GO
INSERT INTO NHAN_VIEN (HoTen, NgaySinh, DiaChi, SDT, Email, VaiTro, Luong, Username, MaTT)
VALUES (N'Nguyễn A', '2000-05-12', N'Quận 8 TPHCM', '0123451211', 'nhanvien1@gmail.com', 'NV', 10000000, 'nhanvien1', 1);
INSERT INTO NHAN_VIEN (HoTen, NgaySinh, DiaChi, SDT, Email, VaiTro, Luong, Username, MaTT)
VALUES (N'Nguyễn B', '1995-05-12', N'Quận 10 TPHCM', '0123451211', 'quanly1@gmail.com', 'QL', 20000000, 'quanly1', 1);
GO
ALTER ROLE db_datareader ADD MEMBER nhan_vien;
ALTER ROLE db_datawriter ADD MEMBER nhan_vien;
ALTER ROLE db_datareader ADD MEMBER khach_hang;
ALTER ROLE db_datawriter ADD MEMBER khach_hang;
ALTER ROLE db_datareader ADD MEMBER quan_ly;
ALTER ROLE db_datawriter ADD MEMBER quan_ly;
GRANT EXECUTE ON SCHEMA::dbo TO KHACH_HANG;
GRANT EXECUTE ON SCHEMA::dbo TO NHAN_VIEN;
GRANT EXECUTE ON SCHEMA::dbo TO QUAN_LY;