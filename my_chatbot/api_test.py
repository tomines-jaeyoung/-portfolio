from pykrx import stock
from datetime import datetime

# 1. 조회할 종목의 티커(종목코드)를 지정합니다.
#    네이버 증권 등에서 쉽게 확인할 수 있습니다.
ticker = "005930"  # 삼성전자

# 2. 조회할 날짜를 지정합니다.
#    오늘 날짜를 문자열 형태로 만듭니다.
today = datetime.now().strftime('%Y%m%d')

try:
    # 3. pykrx를 사용해 지정된 날짜의 주가 정보를 가져옵니다.
    #    get_market_ohlcv 함수는 시가, 고가, 저가, 종가, 거래량(OHLCV) 정보를 가져옵니다.
    df = stock.get_market_ohlcv(today, today, ticker)

    # 4. 가져온 데이터에서 필요한 정보를 추출합니다.
    #    데이터가 비어있는지 먼저 확인합니다. (장 마감 전이거나 휴장일 경우)
    if not df.empty:
        # DataFrame의 첫 번째 행(오늘 데이터)에서 '종가' 값을 가져옵니다.
        closing_price = df.iloc[0]['종가']
        print(f"'{today}' 기준 삼성전자({ticker})의 종가는 {closing_price:,}원입니다.")
    else:
        print(f"'{today}' 날짜의 거래 정보가 없습니다. (휴장일 또는 장 시작 전)")

except Exception as e:
    print(f"데이터를 가져오는 중 오류가 발생했습니다: {e}")