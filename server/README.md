# 踩地雷 API Server

## 啟動

### 系統要求

需要先安裝 Node.js: https://nodejs.org/en/download/current

### Setup

切換到本資料夾，執行以下指令
```
npm install
```

### Run

切換到本資料夾，執行以下指令
```
npm start
```

## API 說明

### 取得排行榜紀錄

- http://localhost:3000/api/records
- Method: GET
- 格式: 陣列，每個元素包含 username 和分數，且依照分數排序(大到小)，例如
    ```json
    [
        {
            "user_name": "user1",
            "score": 100
        },
        {
            "user_name": "user2",
            "score": 90
        }
    ]
    ```

### 新增排行榜紀錄

- http://localhost:3000/api/records/\<name>/\<score>
- Method: POST
