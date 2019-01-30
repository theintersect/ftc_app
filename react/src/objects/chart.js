export default class ChartObject {
  constructor(title, xLabel, dataTypes, data = []) {
    this.title = title;
    this.xLabel = xLabel;
    this.dataTypes = dataTypes;
    this.data = data;
  }

  clear() {
    return new ChartObject(this.title, this.xLabel, this.dataTypes, []);
  }

  // i.e. if datatypes = ["error", "p", "i", "d"], data = [-10.34, -5.16, 3.4, -3.4]
  addData(xValue, data) {
    if (data.length !== this.dataTypes.length) {
      throw new Error("Chart data does not contain all data types.");
    }
    let newData = [...this.data];
    let datapoint = {};
    datapoint[this.xLabel] = xValue;
    for (let i = 0; i < this.dataTypes.length; i++) {
      datapoint[this.dataTypes[i]] = data[i];
    }
    console.log(datapoint);
    newData.push(datapoint);
    return new ChartObject(this.title, this.xLabel, this.dataTypes, newData);
  }
}
