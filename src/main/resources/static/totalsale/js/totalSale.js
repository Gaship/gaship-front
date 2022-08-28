const getTotalSale = async () => {
  const startDate = document.getElementById("startDate").value;
  const endDate = document.getElementById("endDate").value;
  const csrfToken = document.getElementById("_csrf").getAttribute("content");

  const response = await fetch(`/admin/total-sale`, {
    method: 'POST',
    headers: {
      "Content-Type": "application/json",
      'X-CSRF-TOKEN': csrfToken
    },
    body: JSON.stringify({startDate, endDate}),
  });
  const result = await response.json();

  const totalTable = document.getElementById("total-sale-table");

  totalTable.innerHTML = "";

  result.forEach(saleResult => {
    const trTag = document.createElement("tr");
    const totalSaleDateTag = document.createElement("td");
    const orderSaleCntTag = document.createElement("td");
    const orderCancelCntTag = document.createElement("td");
    const orderCntTag = document.createElement("td");
    const orderSaleAmountTag = document.createElement("td");
    const cancelAmountTag = document.createElement("td");
    const totalAmountTag = document.createElement("td");
    totalSaleDateTag.innerHTML = saleResult.totalSaleDate;
    orderSaleCntTag.innerHTML = saleResult.orderSaleCnt;
    orderCancelCntTag.innerHTML = saleResult.orderCancelCnt;
    orderCntTag.innerHTML = saleResult.orderCnt;
    orderSaleAmountTag.innerHTML = saleResult.orderSaleAmount;
    cancelAmountTag.innerHTML = saleResult.cancelAmount;
    totalAmountTag.innerHTML = saleResult.totalAmount;

    trTag.append(totalSaleDateTag, orderCntTag, orderCancelCntTag, orderSaleCntTag,
    orderSaleAmountTag, cancelAmountTag, totalAmountTag);

    totalTable.append(trTag);
  })
  google.charts.load('current', {'packages': ['corechart']});
  google.charts.setOnLoadCallback(drawVisualization);

  function drawVisualization() {
    const totalSaleArray = [];

    totalSaleArray.push(['일자', '판매 매출', '취소 금액', '총 매출']);

    result.forEach( saleResult => {


      totalSaleArray.push(
          [
            saleResult.totalSaleDate,
            saleResult.orderSaleAmount,
            saleResult.cancelAmount,
            saleResult.totalAmount
          ]
      );
    })

    var data = google.visualization.arrayToDataTable(totalSaleArray);

    var options = {
      title: '매출현황',
      vAxis: {title: '매출액'},
      hAxis: {title: '일자'},
      seriesType: 'bars',
      series: {5: {type: 'line'}}
    };

    var chart = new google.visualization.ComboChart(
        document.getElementById('chart_div'));
    chart.draw(data, options);
  }
}

document.querySelector(".total-sale").addEventListener("click", getTotalSale);
