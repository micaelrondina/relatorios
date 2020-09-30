import { Component, Input, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { MatSelectionList } from '@angular/material';


const INPUT_FIELD_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => ListaSelecaoComponent),
  multi: true
};

@Component({
  selector: 'lista-selecao',
  templateUrl: './lista-selecao.component.html',
  styleUrls: ['./lista-selecao.component.css'],
  providers: [INPUT_FIELD_VALUE_ACCESSOR]
})
export class ListaSelecaoComponent implements ControlValueAccessor {

  @Input() msgLista: string;
  @Input() valores: [];
  @Input() marcarTodasOff: false;
  filtro: string;
  marcarTodas = false;


  private innerValue: any;

  get value() {
    return this.innerValue;
  }

  set value(v: any) {
    if (v !== this.innerValue) {
      this.innerValue = v;
      this.onChangeCb(v);
    }
  }

  onChangeCb: (_: any) => void = () => { };
  onTouchedCb: (_: any) => void = () => { };

  writeValue(v: any): void {
    this.value = v;
  }

  registerOnChange(fn: any): void {
    this.onChangeCb = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouchedCb = fn;
  }

  selectAll(matSelectionList: MatSelectionList) {
    if (!this.marcarTodas) {
      matSelectionList.selectAll();
    } else {
      matSelectionList.deselectAll();
    }
  }

  limparFiltro() {
    if (this.filtro !== null
      || this.filtro !== undefined
      || this.filtro.trim() !== '') {
      this.filtro = null;
      this.marcarTodas=false;
    }
  }

  filtrar(valor: string): boolean {
    return !(
      this.filtro === null
      || this.filtro === undefined
      || this.filtro.trim() === ''
      || valor.toLocaleLowerCase().includes(this.filtro.toLocaleLowerCase())
    );
  }

  disabledSelectAll(): boolean {
    return !(
      this.filtro === null
      || this.filtro === undefined
      || this.filtro.trim() === ''
    );
  }
}
