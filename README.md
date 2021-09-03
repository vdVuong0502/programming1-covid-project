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
Class summary xong. 2 list, 1 list gom cac group, 1 list gom result. 1 bien chua resultType, 1 bien chua metric
