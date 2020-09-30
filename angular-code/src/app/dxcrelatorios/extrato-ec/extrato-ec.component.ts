import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar, MatTableDataSource, MatPaginator, MatSort, Sort } from '@angular/material';
import Utils from '../utils';
import { Subscription } from 'rxjs';
import { ExtratoEC } from '../../shared/models/extratoEC';

import { ExportExcelService } from '../../shared/services/export-excel.service';
import { ExtratoECService } from '../../shared/services/extrato-ec.service';



@Component({
  selector: 'app-extrato-ec',
  templateUrl: './extrato-ec.component.html',
  styleUrls: ['./extrato-ec.component.css']
})
export class ExtratoECComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) tableSort: MatSort;

  exibirBtnExport: boolean = false;

  dataSource: any = null;

  displayedColumns: string[] = [
    'Comercio',
    'Filial',
    'Produto',
    'Rubrica',
    'Data Transação',
    'Data Vencimento',
    'Data Liquidação',
    'Descrição',
    'Valor',
    'Saldo',

  ];
  private sub: Subscription[] = [];
  extratoEC: ExtratoEC[];
  formSearch: FormGroup;



  constructor(private extratoECService: ExtratoECService,
    private _formBuilder: FormBuilder,
    private snackBar: MatSnackBar,
    private exportExcelService: ExportExcelService) { }

  ngOnInit() {
    this.formSearch = this._formBuilder.group({
      dataInicio: [this.getDataInicio(), Validators.required],
      dataFim: [this.getDataFim(), Validators.required]
    });


  }

  ngOnDestroy() {
    //fazendo o unsubscribe de todos os subscription feitos
    this.sub.forEach(s => s.unsubscribe());
  }


  exportAsXLSX(): void {
    this.exportExcelService.exportToExcel(this.dataSource.data, 'Relatorio-');
  }

  criarTabela() {
    this.dataSource = new MatTableDataSource(this.extratoEC);
  }



  receberObjetoFiltro(objeto: any): void {
    this.resetPesquisa();
    this.sub.push(this.extratoECService.getListClubSaude(objeto).subscribe(v => {
      if (v.dados != null && v.dados.length > 0) {
        this.extratoEC = v.dados;
        this.criarTabela();
        this.exibirBtnExport = true;

      } else {
        this.openSnackBar("Nenhum dado encontrado com esse filtro", "");
      }
    }, (error: any) => this.openSnackBar(Utils.getError(error), "Ocorreu um erro!")
    ));
  }

  exibirBotaoExport() {
    return this.exibirBtnExport;
  }
  resetPesquisa() {
    this.extratoEC = null;
    this.dataSource = null; //como a grid esta vinculada ao datasouce, tenho que limpar ele para o conteudo da grid sumir
    this.exibirBtnExport = false;
    Utils.removerOrdenacaoTable(this.tableSort);
  }

  sortData(sort: Sort) {
    // this.listIncoming = Utils.ordenarLista(this.dataSource, sort);
    this.extratoEC = Utils.ordenarLista(this.extratoEC, sort);
    this.criarTabela();
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 4000,
    });
  }

  getRequiredMessage(campo: string) {
    return this.formSearch.get(campo).hasError('required') ? 'Campo Obrigatório!' : '';
  }
  protected getDataInicio(): number {
    return null;
  }

  protected getDataFim(): number {
    return null;
  }

}
