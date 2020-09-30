import { HttpErrorResponse } from "@angular/common/http/src/response";
import { Sort, MatSort } from "@angular/material";
export default class Utils {

    static getError(error: HttpErrorResponse): string {
        console.log(error);
        if (error.status > 0) {
            return "Erro " + error.error.falha.message
        } else {
            return error.status + "-" + error.statusText;
        }
    }

    static compare(a: number | string, b: number | string, isAsc: boolean) {
        return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
    }


    static ordenarLista(dataSource: any, sort: Sort) {
        if (dataSource == null) {
            return;
        }

        //se nao tem ordenacao, retorna o DataSource
        if (!sort.active || sort.direction === '') {
            // return dataSource.filteredData;
            return dataSource;
        }

        // const data = dataSource.filteredData.slice();
        const data = dataSource.slice();

        //ordenando
        var dataSourceFilteredDataOrdened = data.sort((a, b) => {
            const isAsc = sort.direction === 'asc';
            switch (sort.active) {
                case 'Comercio': return Utils.compare(a.comercio, b.comercio, isAsc);
                case 'Data Vencimento': return Utils.compare(a.dataVencimento, b.dataVencimento, isAsc);
                case 'Produto': return Utils.compare(a.producto, b.producto, isAsc);
                case 'Descrição': return Utils.compare(a.descricao, b.descricao, isAsc);
                case 'Data Transação': return Utils.compare(a.dataTransacao, b.dataTransacao, isAsc);
                case 'Data Liquidação': return Utils.compare(a.dataLiquidacao, b.dataLiquidacao, isAsc);
                case 'Saldo': return Utils.compare(a.saldo, b.saldo, isAsc);
                case 'Filial': return Utils.compare(a.filial, b.filial, isAsc);
                case 'Rubrica': return Utils.compare(a.rubrica, b.rubrica, isAsc);
                default: return 0;
            }
        });
        return dataSourceFilteredDataOrdened;
    }

    static removerOrdenacaoTable(tableSort: MatSort) {
        tableSort.sort({ id: "", start: 'asc', disableClear: false }); //tirando a ordenacao
    }






}