# ----------------------------------------------------------------
# chatbot_server.py (종목명 직접 검색 버전)
# ----------------------------------------------------------------
from flask import Flask, request, render_template
import os
from pykrx import stock
from datetime import datetime
import time

app = Flask(__name__)

TICKER_NAME_MAP = {}


def initialize_ticker_map():
    print("서버 시작! 전체 종목 코드와 종목명 매핑을 시작합니다...")
    start_time = time.time()

    kospi_tickers = stock.get_market_ticker_list(market="KOSPI")
    kosdaq_tickers = stock.get_market_ticker_list(market="KOSDAQ")
    all_tickers = kospi_tickers + kosdaq_tickers

    for ticker in all_tickers:
        name = stock.get_market_ticker_name(ticker)
        TICKER_NAME_MAP[name] = ticker

    end_time = time.time()
    print(f"매핑 완료! 총 {len(TICKER_NAME_MAP)}개의 종목이 로드되었습니다. (소요 시간: {end_time - start_time:.2f}초)")


BASE_DIR = os.path.dirname(os.path.abspath(__file__))
DB_PATH = os.path.join(BASE_DIR, 'qa_db.txt')


def load_db():
    qa_pairs = {}
    try:
        with open(DB_PATH, 'r', encoding='utf-8') as f:
            for line in f:
                parts = line.strip().split(',')
                if len(parts) == 2:
                    qa_pairs[parts[0]] = parts[1]
    except FileNotFoundError:
        qa_pairs['안녕'] = '데이터 파일을 찾을 수 없어요.'
    return qa_pairs


def find_answer(user_question, db):
    if user_question in db:
        return db[user_question]
    for question, answer in db.items():
        if user_question in question:
            return answer
    return "무슨 말인지 잘 모르겠어요."


def get_stock_info(ticker):
    try:
        today = datetime.now().strftime('%Y%m%d')
        lookup_day = stock.get_nearest_business_day_in_a_week(date=today)

        df_ohlcv = stock.get_market_ohlcv(lookup_day, lookup_day, ticker)
        df_cap = stock.get_market_cap(lookup_day, lookup_day, ticker)

        if df_ohlcv.empty or df_cap.empty:
            return "해당 날짜의 주식 정보를 찾을 수 없습니다."

        info = df_ohlcv.iloc[0]
        market_cap = df_cap.iloc[0]['시가총액']

        response = (
            f"--- [{stock.get_market_ticker_name(ticker)}] ---\n"
            f"주식 정보 ({lookup_day})\n\n"
            f"시가: {info['시가']:,}원\n"
            f"고가: {info['고가']:,}원\n"
            f"저가: {info['저가']:,}원\n"
            f"거래량: {info['거래량']:,}주\n"
            f"시가총액: {market_cap:,}원"
        )
        return response

    except Exception as e:
        print(f"Error fetching stock data for {ticker}: {e}")
        return "주가 정보를 가져오는 데 실패했습니다."


@app.route('/', methods=['GET', 'POST'])
def chat():
    answer = ""
    style = "white-space: pre-wrap;"
    if request.method == 'POST':
        user_question = request.form.get('question', '').strip()

        # [수정된 로직] 사용자가 입력한 내용이 주식 이름 목록(TICKER_NAME_MAP)에 있는지 바로 확인
        if user_question in TICKER_NAME_MAP:
            ticker = TICKER_NAME_MAP[user_question]
            answer = get_stock_info(ticker)
        else:
            # 주식 이름이 아니면, 기존 DB에서 답변을 검색
            qa_database = load_db()
            answer = find_answer(user_question, qa_database)

    return render_template('index.html', answer=answer, style=style)


if __name__ == '__main__':
    initialize_ticker_map()
    app.run(host='0.0.0.0', port=5000, debug=True)