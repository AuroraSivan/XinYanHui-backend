CREATE VIEW ConsultantDailyAvailability AS
WITH RECURSIVE DateRange AS (
    SELECT CURDATE() + INTERVAL 1 DAY AS available_date
    UNION ALL
    SELECT available_date + INTERVAL 1 DAY
    FROM DateRange
    WHERE available_date < CURDATE() + INTERVAL 6 DAY
),
Hours AS (
    SELECT 8 AS hour UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12
    UNION ALL SELECT 13 UNION ALL SELECT 14 UNION ALL SELECT 15 UNION ALL SELECT 16 UNION ALL SELECT 17
),
ConsultantHours AS (
    SELECT 
        c.consultant_id,
        dr.available_date,
        h.hour,
        cs.status,
        cs.start_time,
        cs.end_time,
        cs.slot_capacity
    FROM 
        Consultants c
    CROSS JOIN 
        DateRange dr
    CROSS JOIN 
        Hours h
    INNER JOIN 
        ConsultantSchedules cs 
        ON c.consultant_id = cs.consultant_id 
        AND cs.available_date = dr.available_date 
        AND cs.start_time <= h.hour 
        AND h.hour < cs.end_time 
        AND cs.status = 'normal'
),
AppointmentsCount AS (
    SELECT 
        consultant_id,
        appointment_date,
        HOUR(appointment_time) AS hour,
        COUNT(*) AS appointment_count
    FROM 
        Appointments
    WHERE 
        appointment_date BETWEEN CURDATE() + INTERVAL 1 DAY AND CURDATE() + INTERVAL 6 DAY
        AND HOUR(appointment_time) BETWEEN 8 AND 17
        AND status = 'booked'
    GROUP BY 
        consultant_id, appointment_date, HOUR(appointment_time)
),
HourlyAvailability AS (
    SELECT 
        ch.consultant_id,
        ch.available_date,
        ch.hour,
        CASE 
            WHEN ch.status = 'normal' 
                 AND ch.start_time <= ch.hour 
                 AND ch.hour < ch.end_time 
                 AND (ac.appointment_count IS NULL OR ac.appointment_count < ch.slot_capacity) 
            THEN 1 
            ELSE 0 
        END AS available
    FROM 
        ConsultantHours ch
    LEFT JOIN 
        AppointmentsCount ac 
        ON ch.consultant_id = ac.consultant_id 
        AND ch.available_date = ac.appointment_date 
        AND ch.hour = ac.hour
)
SELECT 
    consultant_id,
    available_date,
    MAX(CASE WHEN hour = 8 THEN available ELSE 0 END) AS hour_8_available,
    MAX(CASE WHEN hour = 9 THEN available ELSE 0 END) AS hour_9_available,
    MAX(CASE WHEN hour = 10 THEN available ELSE 0 END) AS hour_10_available,
    MAX(CASE WHEN hour = 11 THEN available ELSE 0 END) AS hour_11_available,
    MAX(CASE WHEN hour = 12 THEN available ELSE 0 END) AS hour_12_available,
    MAX(CASE WHEN hour = 13 THEN available ELSE 0 END) AS hour_13_available,
    MAX(CASE WHEN hour = 14 THEN available ELSE 0 END) AS hour_14_available,
    MAX(CASE WHEN hour = 15 THEN available ELSE 0 END) AS hour_15_available,
    MAX(CASE WHEN hour = 16 THEN available ELSE 0 END) AS hour_16_available,
    MAX(CASE WHEN hour = 17 THEN available ELSE 0 END) AS hour_17_available
FROM 
    HourlyAvailability
GROUP BY 
    consultant_id, available_date
ORDER BY 
    consultant_id, available_date;