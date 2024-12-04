import json
import pymysql

# JSON 파일 경로
json_file_path = r"C:\Users\gwony\Downloads\KS_SVCH_UTILIIZA_CRSTAT_INFO_202410.json"

# MySQL 연결 정보
db_config = {
    "host": "localhost",       # MySQL 호스트
    "user": "root",            # MySQL 사용자
    "password": "1234",    # MySQL 비밀번호
    "database": "fithandb"     # 사용할 데이터베이스
}

# JSON 파일 읽기
with open(json_file_path, 'r', encoding='utf-8') as file:
    data = json.load(file)

# MySQL 연결
connection = pymysql.connect(**db_config)
cursor = connection.cursor()

# 데이터 삽입
insert_query = """
    INSERT INTO sports_voucher_usage (
        BSNS_NO, FCLTY_NM, ITEM_CD, ITEM_NM, CTPRVN_CD, CTPRVN_NM, SIGNGU_CD, SIGNGU_NM,
        FCLTY_ADDR, FCLTY_DETAIL_ADDR, ZIP_NO, TEL_NO, COURSE_NM, COURSE_NO, COURSE_ESTBL_YEAR,
        COURSE_ESTBL_MT, COURSE_BEGIN_DE, COURSE_END_DE, COURSE_REQST_NMPR_CO, COURSE_PRC
    ) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
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
            record['COURSE_NO'],
            record['COURSE_ESTBL_YEAR'],
            record['COURSE_ESTBL_MT'],
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

print("데이터베이스에 데이터가 저장되었습니다.")
