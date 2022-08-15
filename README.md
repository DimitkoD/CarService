# CarService
Идея. Ще се разработи система за коли под наем. Ще има CRUD контролер за добавяне, ъпдейтване, показване, изтриване на коли. Ще има сървис за базата данни и CRUD заявките. Ще има друг сървиз за връзка с външното апи и един сървър за бизнес логиката. Още не съм измислил какво апи ще се ползва дали по ВИН или по ГПС координати на колите.

Модели: Car -> Vin, type, brand, model, price;
Client -> Id, Name, Address;
Employee -> Id, Name, Address, Position; Rent -> Id, CarId, ClientId, EmployeeId, Date;

Бизнес логика:

•	CRUD заявки за коли.

•	Хора, които ще наемат колите, които ще се добавят допълнително, ако ги няма.

•	Списък със свободни коли и цената им, идваща от сървиза с цената

•	Заемане на кола. •	Връщане на кола.

•	Проверка на местоположението на дадена кола по нейните GPS координати и изкарване на локация на карта от АПИ-то или изкарване на данните за колата по вин номера й

•	Да не могат да се заемат две коли едновременно от един човек. Може би ще добавя служители и съответно изкарване на служителите, подредени по брой отдадени коли под наем.
