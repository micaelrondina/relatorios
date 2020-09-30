import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { MatSnackBar } from '@angular/material';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { ExtratoEC } from '../../shared/models/extratoEC';
import { AdquirentesService } from '../../shared/services/adquirentes.service';
import Utils from '../utils';
import { ApiRequest, FiltroConsultar } from '../extrato-ec/extrato-ec';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-filtro-consulta',
  templateUrl: './filtro-consulta.component.html',
  styleUrls: ['./filtro-consulta.component.css']
})
export class FiltroConsultaComponent implements OnInit {
  private sub: Subscription[] = [];

  @Input() titulo: string = null; //Label titulo da card
  @Output() objectFiltro = new EventEmitter();
  @Output() changeSearch = new EventEmitter();
  @Input() exibirBtnExport: boolean = false; //se false, nao exibe nunca o botao de Export
  @Output() onExport = new EventEmitter();

  objectRequest: ApiRequest<FiltroConsultar>;
  formSearch: FormGroup;
  rubricaFormControl: FormControl;
  clubSaude: ExtratoEC[];
  dataInicio: Date;
  dataFim: Date;
  rubrica: number;
  comercio: number;
  listAdquirentes: any = null;
  produto: number;
  filial: number;

  constructor(private snackBar: MatSnackBar,
    private _formBuilder: FormBuilder,
    private AdquirenteService: AdquirentesService) { }

  ngOnInit() {

    this.formSearch = this._formBuilder.group({
      dataInicio: [this.getDataInicio(), Validators.required],
      dataFim: [this.getDataFim(), Validators.required],
      rubrica: [this.getComercio(), Validators.pattern("^[0-9]*$")],
      comercio: [this.getRubrica(), Validators.pattern("^[0-9]*$")],
      produto: [this.getProduto(), Validators.pattern("^[0-9]*$")],
      filial: [this.getFilial(), Validators.pattern("^[0-9]*$")],
      acquirerId: [this.getAdiquirente(), Validators.required],
    });

    this.sub.push(this.AdquirenteService.getAdquirentes().subscribe(v => {
      this.listAdquirentes = v.dados
    }, (error: any) => this.openSnackBar(Utils.getError(error), "Ocorreu um erro!")
    ));


  };

  ngOnDestroy() {
    //fazendo o unsubscribe de todos os subscription feitos
    this.sub.forEach(s => s.unsubscribe());
  }

  exibirBotaoExport() {
    return this.exibirBtnExport;
  }

  clickExport() {
    this.onExport.emit(true);
  }


  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 4000,
    });
  }

  changeParametros(): void {
    this.changeSearch.emit(true);
  }


  onSubmitSearch(): void {
    if (!this.formSearch.valid) {
      this.openSnackBar("Dados Invalidos", ":(");
    } else {
      //validando se DataFim eh Maior ou Igual a Data Inicio
      //Seta as informações no SharedService (essas informação são usadas para exportar os dados para Excel)
      if (this.formSearch.get('dataFim').value < this.formSearch.get('dataInicio').value) {
        this.openSnackBar("Dados Invalidos - Data Iní­cio deve ser menor ou igual Data Fim", ":(");
      } else {
        // this.dataInicio = this.formSearch.get('dataInicio').value,
        // this.dataFim = this.formSearch.get('dataFim').value

        if (this.formSearch.get('rubrica').value == null || this.formSearch.get('rubrica').value == 0) {
          this.rubrica = -1;
        } else {
          this.rubrica = this.formSearch.get('rubrica').value;
        }

        if (this.formSearch.get('comercio').value == null || this.formSearch.get('comercio').value == 0) {
          this.comercio = -1;
        } else {
          this.comercio = this.formSearch.get('comercio').value;
        }

        if (this.formSearch.get('produto').value == null || this.formSearch.get('produto').value == 0) {
          this.produto = -1;
        } else {
          this.produto = this.formSearch.get('produto').value;
        }

        if (this.formSearch.get('filial').value == null || this.formSearch.get('filial').value == 0) {
          this.filial = -1;
        } else {
          this.filial = this.formSearch.get('filial').value;
        }

        this.objectRequest = <ApiRequest<FiltroConsultar>>{
          filtro: {
            "acquirerId": this.formSearch.get('acquirerId').value.acquirerId,
            "dataInicio": this.formSearch.get('dataInicio').value,
            "dataFim": this.formSearch.get('dataFim').value,
            "rubrica": this.rubrica,
            "comercio": this.comercio,
            "produto": this.produto,
            "filial": this.filial,
          }
        }
        this.objectFiltro.emit(this.objectRequest);
      }

    }

  }

  protected getFilial(): number {
    return null;
  }

  protected getProduto(): number {
    return null;
  }

  protected getComercio(): number {
    return null;
  }

  protected getRubrica(): number {
    return null;
  }

  protected getDataInicio(): string {
    return null;
  }

  protected getDataFim(): string {
    return null;
  }

  protected getAdiquirente(): number {
    return null;
  }

  getRequiredMessage(campo: string) {
    return this.formSearch.get(campo).hasError('required') ? 'Campo Obrigatório!' : '';
  }

}









