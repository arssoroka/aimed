This is a Spring Boot application, so it should be run as every  Spring Boot app:
mvn clean install && 
mvn spring-boot:run

It implements custom scheduler which is managed by two parameters :
app.feature.useScheduler, app.periodicity

app.feature.useScheduler works as feature toggle and app.periodicity can take the 
following values : day, hour, week. First execution will be on the next hour if 
periodicity=hour and start of the next day if periodicity=day or periodicity=week.

Alternatively, populating table of Treatment_Task can also be produced manually with 
hitting endpoint POST /task with query parameters localDateTime, and useStartOfDay.
If localDateTime is not provided then it uses current localDateTime, if useStartOfDay
is provided then it provides result on start of the day.

Two algorithms are implemented. The first one is a straightforward parsing of 
recurrence_pattern field of treatment_plan table in each record - which is not very fast,
but it meets all requirements. Alternatively parsing of recurrence_pattern can be done
when treatment_plan table is populated by just using RecurringPatternParser.parse() and
saving additionally fields recurrence_start_time, recurrence_end_time and 
recurrence_periodicity. recurrence_periodicity can take values from 0-7, 1-7 denote 
days of week and 0 means 'every day'. In this case we can use POST /task/alternative endpoint.
In this case recurrence_start_time, recurrence_end_time, recurrence_periodicity should be populated
in advance.

RecurringPatternParser can parse recurring periods of single value e.g. 
"every day from 08:00 to 18:00" or from-to periods e.g. 
"every Friday from 08:00 to 18:00 and from 20:00 to 21:00". 

Algorithm consider every record in Treatment_plan table as either recurring OR between 
start_time and end_time (not recurring).


