document.addEventListener('DOMContentLoaded', function () {

    // Получение сегодняшней даты
    var today = new Date();
    var tomorrow = new Date();
    tomorrow.setDate(today.getDate() + 1);

// Форматирование даты в YYYY-MM-DD
    var ddToday = String(today.getDate()).padStart(2, '0');
    var mmToday = String(today.getMonth() + 1).padStart(2, '0');
    var yyyyToday = today.getFullYear();
    var formattedToday = yyyyToday + '-' + mmToday + '-' + ddToday;

// Форматирование завтрашней даты в YYYY-MM-DD
    var ddTomorrow = String(tomorrow.getDate()).padStart(2, '0');
    var mmTomorrow = String(tomorrow.getMonth() + 1).padStart(2, '0');
    var yyyyTomorrow = tomorrow.getFullYear();
    var formattedTomorrow = yyyyTomorrow + '-' + mmTomorrow + '-' + ddTomorrow;

// Установка минимальных дат
    document.getElementById('checkInDate').setAttribute('min', formattedToday);
    document.getElementById('checkOutDate').setAttribute('min', formattedTomorrow);

    document.getElementById('checkInDate').addEventListener('change', function () {
        var checkInDate = new Date(this.value);
        checkInDate.setDate(checkInDate.getDate() + 1);

        var dd = String(checkInDate.getDate()).padStart(2, '0');
        var mm = String(checkInDate.getMonth() + 1).padStart(2, '0');
        var yyyy = checkInDate.getFullYear();
        var minCheckOutDate = yyyy + '-' + mm + '-' + dd;

        document.getElementById('checkOutDate').setAttribute('min', minCheckOutDate);
    });
})
