# về cơ bản cái flow của chương trình mình nghĩ nó sẽ như này:

tạo một object để lưu data đã được lọc sau khi chọn area và time range

- chạy chương trình -> chọn area (có thể là continent hoặc country)
  -> chọn nhóm thời gian
  object lúc này sẽ lưu: + name (tên continent hoặc country) + date + new cases + new deaths + vaccinated people
  ---------------date, cases, deaths,vaccinated people sẽ có cùng index theo số liệu của date
  ======

summary sẽ tạo thêm 1 class nữa và xử lý dựa trên dữ liệu đã được lọc của Data

in data dưới dạng chart hoặc table thì sẽ nằm chung trong 1 class khác nữa
chi tiết mình sẽ bàn lúc họp
people vaccinated data sẽ được handle khi chỉnh data theo time range.

Đã xong lớp Data:

- NewCase list
- NewDeath list
- PeopleVaccinated list
- Date list
- start date
- end date

data trong các list đã được sort theo date. Mình dùng LocalDate cho các biển date và DateTimeFormatter để format
Data sẽ được lọc trong khoảng (start date - 1) cho đến (end date + 1). (nếu start date với end date được chọn đã trùng với 2 đầu list thì không thêm)
Lý do mình thêm vào mỗi 2 đầu 1 thêm 1 ngày để cho trường hợp user chọn no group, và chọn result type là upto ở class Summary thì mình còn so sánh với ngày trước nó được. có lẽ không cần thên (end date + 1) đâu nhưng cứ giữ. cần thì mình dùng, không thì để đó không sao
