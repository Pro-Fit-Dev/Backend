import json
import pymysql

# JSON 파일 경로
json_file_path = r"파일 주소\sports2.json"

# MySQL 연결 정보
db_config = {
    "host": "localhost",       # MySQL 호스트
    "user": "root",            # MySQL 사용자
    "password": "1234",        # MySQL 비밀번호
    "database": "fithandb"     # 사용할 데이터베이스
}

# MySQL 연결
connection = pymysql.connect(**db_config)
cursor = connection.cursor()

# 테이블 생성
create_table_query = """
CREATE TABLE IF NOT EXISTS disabled_person_ports_voucher_usage (
    BSNS_NO VARCHAR(10) NOT NULL,
    FCLTY_NM VARCHAR(200) NOT NULL,
    ITEM_CD VARCHAR(30) NOT NULL,
    ITEM_NM VARCHAR(200) NOT NULL,
    CTPRVN_CD VARCHAR(30) NOT NULL,
    CTPRVN_NM VARCHAR(200) NOT NULL,
    SIGNGU_CD VARCHAR(30) NOT NULL,
    SIGNGU_NM VARCHAR(200) NOT NULL,
    FCLTY_ADDR VARCHAR(200) NOT NULL,
    FCLTY_DETAIL_ADDR VARCHAR(200) DEFAULT NULL,
    ZIP_NO VARCHAR(5) NOT NULL,
    TEL_NO VARCHAR(20) DEFAULT NULL,
    COURSE_NM VARCHAR(200) NOT NULL,
    TROBL_TY_NM VARCHAR(200) DEFAULT NULL,
    COURSE_NO VARCHAR(20) NOT NULL,
    COURSE_ESTBL_YEAR VARCHAR(4) DEFAULT NULL,
    COURSE_ESTBL_MT VARCHAR(2) DEFAULT NULL,
    COURSE_BEGIN_DE VARCHAR(8) NOT NULL,
    COURSE_END_DE VARCHAR(8) NOT NULL,
    COURSE_REQST_NMPR_CO DECIMAL(38) NOT NULL,
    COURSE_PRC DECIMAL(28, 5) NOT NULL,
    PRIMARY KEY (BSNS_NO, COURSE_NO)
);
"""
cursor.execute(create_table_query)

# JSON 파일 읽기
with open(json_file_path, 'r', encoding='utf-8') as file:
    data = json.load(file)

# 데이터 삽입
insert_query = """
    INSERT INTO disabled_person_ports_voucher_usage (
        BSNS_NO, FCLTY_NM, ITEM_CD, ITEM_NM, CTPRVN_CD, CTPRVN_NM, SIGNGU_CD, SIGNGU_NM,
        FCLTY_ADDR, FCLTY_DETAIL_ADDR, ZIP_NO, TEL_NO, COURSE_NM, TROBL_TY_NM, COURSE_NO,
        COURSE_ESTBL_YEAR, COURSE_ESTBL_MT, COURSE_BEGIN_DE, COURSE_END_DE, COURSE_REQST_NMPR_CO, COURSE_PRC
    ) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
"""

for record in data:
    try:
        cursor.execute(insert_query, (
            record['BSNS_NO'],
            record['FCLTY_NM'],
            record['ITEM_CD'],
            record['ITEM_NM'],
            record['CTPRVN_CD'],
            record['CTPRVN_NM'],
            record['SIGNGU_CD'],
            record['SIGNGU_NM'],
            record['FCLTY_ADDR'],
            record.get('FCLTY_DETAIL_ADDR', None),  # NULL 허용
            record['ZIP_NO'],
            record.get('TEL_NO', None),            # NULL 허용
            record['COURSE_NM'],
            record.get('TROBL_TY_NM', None),       # NULL 허용
            record['COURSE_NO'],
            record.get('COURSE_ESTBL_YEAR', None), # NULL 허용
            record.get('COURSE_ESTBL_MT', None),   # NULL 허용
            record['COURSE_BEGIN_DE'],
            record['COURSE_END_DE'],
            record['COURSE_REQST_NMPR_CO'],
            record['COURSE_PRC']
        ))
    except KeyError as e:
        print(f"Missing key: {e}")
    except Exception as e:
        print(f"Error inserting record: {e}")

# 커밋 및 연결 종료
connection.commit()
cursor.close()
connection.close()

print("테이블 생성 및 데이터 삽입이 완료되었습니다.")
