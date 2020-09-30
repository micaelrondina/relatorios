import { Injectable } from '@angular/core';
import * as FileSaver from 'file-saver';
import * as XLSX from 'xlsx';

const EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8';
const EXCEL_EXT = '.xlsx';


@Injectable({
  providedIn: 'root'
})


export class ExportExcelService {

  constructor() { }

  exportToExcel(json: any[], excelFileName: string): void {
    const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(json);
    const workbook: XLSX.WorkBook = {
      Sheets: { 'Extrato EC': worksheet },
      SheetNames: ['Extrato EC']
    };
    const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
    //chamar metodo buffeer e filename 
    this.saveAsExcel(excelBuffer, excelFileName);
  }

  private saveAsExcel(buffer: any, fileName: string): void {
    const data: Blob = new Blob([buffer], { type: EXCEL_TYPE })

    FileSaver.saveAs(data, fileName + 'Extrato-' + this.montaDateYYYYmmdd_HHmmss() + EXCEL_EXT);

  }

  private montaDateYYYYmmdd_HHmmss(): any {
    var dt = new Date().toLocaleString("BR", { timeZone: "America/Recife" })
    var retorno: string =
      dt.substring(0, 2) //dia
      + dt.substring(2, 4) //mes
      + dt.substring(4, 9) //ano
      + "_"
      + dt.substring(11).replace(":", "").replace(":", ""); //hora minuto segundo
    return retorno;
  }

}
