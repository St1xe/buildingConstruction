# buildingConstruction

Building construction system based on csv, xml and PostgreSQL data sources.


java -Dconfig=<path_to_properties> -Dlog4j.configuration=file:<path_to_log4j_configuration_file> -jar <название_файла> 
Если -Dconfig не указан, будет использовано значение по умолчанию.
Если -Dlog4j.configuration не указан, будет использовано значение по умолчанию

------------------------------------------------------------------------------------------
 Команды для запуска.
 //каждая команда принимает параметр [dataProvider] для выбора провайдера. Допустимые значения: DB; CSV; XML

Если [dataProvider] не указан, используется dataProviderCsv по умолчанию
------------------------------------------------------------------------------------------
 
 
 //создание нового владельца
  java -jar buildingConstruction.jar [dataProvider] -owner  [ownerId] [ownerName] [ownerPhone] [ownerEmail] [ownerPassport]

//пример:

java -jar buildingConstruction.jar -owner 1234 Иван 89515467834 xxx@mail.ru 6034567435
 
------------------------------------------------------------------------------------------

 //создание материала
  java -jar buildingConstruction.jar [dataProvider] -material  [materialId] [material Name] [materialPrice] [quantityInStock]

//пример:
java -jar  buildingConstruction.jar -material 5362 Бетон 1500 234

------------------------------------------------------------------------------------------


 //создание работника
  java -jar buildingConstruction.jar [dataProvider] -worker  [workerId] [workerName] [jobTitle][workerNumber] [workerSalary]

//пример:

java -jar buildingConstruction.jar -worker 37372 Олег Строитель 89567345632 75000 

------------------------------------------------------------------------------------------

 //создание строительного оборудования
  java -jar buildingConstruction.jar [dataProvider] -equipment  [equipmentId] [equipmentName] [equipmentPrice] 

//пример:

 java -jar buildingConstruction.jar -equipment 47363t7 Бетономешалка 35000

------------------------------------------------------------------------------------------

 //подготовка плана строительства
  java -jar buildingConstruction.jar [dataProvider] -plan  ApartmentHouse [square] [numberOfFloors] [numberOfApartments] [client] [materials] [EngineeringSystems] 

//пример:

java -jar buildingConstruction.jar -plan  ApartmentHouse 263 5 30 1234  5362 HEATING,SEWERAGE

------------------------------------------------------------------------------------------

 //подготовка плана строительства
  java -jar buildingConstruction.jar [dataProvider] -plan  House [square] [numberOfFloors] [numberOfRooms] [client] [materials] [EngineeringSystems] 

//пример:
java -jar buildingConstruction.jar -plan  House 63 2 6 1234  5362 HEATING,SEWERAGE

------------------------------------------------------------------------------------------

 //подготовка плана строительства
  java -jar buildingConstruction.jar [dataProvider] -plan  Garage [square] [numberOfFloors] [numberOfCars] [client] [materials] [EngineeringSystems] 

//пример:

java -jar buildingConstruction.jar -plan  Garage 25 1 2 1234  5362 HEATING,SEWERAGE

------------------------------------------------------------------------------------------

 //подготовка к строительству
  java -jar buildingConstruction.jar [dataProvider] -build  [buildingID] [Building] 


//пример:

java -jar buildingConstruction.jar -build 1111 House

------------------------------------------------------------------------------------------
 //расчёт стоимости строительства
  java -jar buildingConstruction.jar [dataProvider] -cost  [buildingID] [Building] 

//пример:

java -jar buildingConstruction.jar -cost 1111 House


# Диаграммы

## Диаграмма вариантов использования
![image](https://github.com/user-attachments/assets/35bb412b-bb81-41b8-abe0-d925e5f1b63a)

## Диаграмма классов
![image](https://github.com/user-attachments/assets/8b9efca5-d7a3-4138-947a-6517a6b7b610)

## Диаграмма деятельности
![image](https://github.com/user-attachments/assets/34ce0a12-6ba4-4e54-bac2-0ff62b64c2c6)

## Диаграмма компонентов
![image](https://github.com/user-attachments/assets/3feb78ec-3c91-45c6-a885-4231f0a7c7e5)

