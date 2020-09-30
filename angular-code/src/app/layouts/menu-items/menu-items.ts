import { Injectable } from '@angular/core';
import { SharedService } from '../../shared/shared.service';

export interface Menu {
  state: string;
  name: string;
  type: string;
  icon: string;
  task: string;
}

const MENUITEMS = [
  { state: 'rel-eventos-1386', type: 'link', name: 'Rel. Eventos 1386', icon: 'file_copy', task: 'dxcrel.eventos.1386' },
  { state: 'ger-filas-emissor', type: 'link', name: 'Geren. Filas - Emissor', icon: 'file_copy', task: 'dxcrel.ger.filas.emissor' },
  { state: 'ger-filas-acquirer', type: 'link', name: 'Geren. Filas - Acquirer', icon: 'file_copy', task: 'dxcrel.ger.filas.acquirer' },
  { state: 'extrato-ec', type: 'link', name: 'Extrato do EC', icon: 'file_copy', task: 'dxcrel.extrato.ec' }
];

@Injectable()
export class MenuItems {
  getMenuitem(): Menu[] {
    let ss = SharedService.getInstance();
    let listItensMenu = [];

    //verificando para quais itens de menu o usuario tem acesso
    if (ss != null && ss.loggedUser != null) {
      MENUITEMS.forEach(element => {
        if (ss.loggedUser.tasks.some(v => v === element.task)) {
          listItensMenu.push(element);
        }
      });
      return listItensMenu;
    } else {
      return MENUITEMS;
    }
  }
}
