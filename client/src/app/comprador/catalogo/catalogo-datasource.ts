import {DataSource} from '@angular/cdk/collections';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {map} from 'rxjs/operators';
import {merge, Observable, of as observableOf} from 'rxjs';

// TODO: Replace this with your own data model type
export interface CatalogoItem {
  descricao: string;
  id: number;
}

// TODO: replace this with real data from your application
const EXAMPLE_DATA: CatalogoItem[] = [
  {id: 1, descricao: 'Hydrogen'},
  {id: 2, descricao: 'Helium'},
  {id: 3, descricao: 'Lithium'},
  {id: 4, descricao: 'Beryllium'},
  {id: 5, descricao: 'Boron'},
  {id: 6, descricao: 'Carbon'},
  {id: 7, descricao: 'Nitrogen'},
  {id: 8, descricao: 'Oxygen'},
  {id: 9, descricao: 'Fluorine'},
  {id: 10, descricao: 'Neon'},
  {id: 11, descricao: 'Sodium'},
  {id: 12, descricao: 'Magnesium'},
  {id: 13, descricao: 'Aluminum'},
  {id: 14, descricao: 'Silicon'},
  {id: 15, descricao: 'Phosphorus'},
  {id: 16, descricao: 'Sulfur'},
  {id: 17, descricao: 'Chlorine'},
  {id: 18, descricao: 'Argon'},
  {id: 19, descricao: 'Potassium'},
  {id: 20, descricao: 'Calcium'},
];

/**
 * Data source for the Catalogo view. This class should
 * encapsulate all logic for fetching and manipulating the displayed data
 * (including sorting, pagination, and filtering).
 */
export class CatalogoDataSource extends DataSource<CatalogoItem> {
  data: CatalogoItem[] = []; // EXAMPLE_DATA;
  paginator: MatPaginator;
  sort: MatSort;

  constructor() {
    super();
  }

  /**
   * Connect this data source to the table. The table will only update when
   * the returned stream emits new items.
   * @returns A stream of the items to be rendered.
   */
  connect(): Observable<CatalogoItem[]> {
    // Combine everything that affects the rendered data into one update
    // stream for the data-table to consume.
    const dataMutations = [
      observableOf(this.data),
      this.paginator.page,
      this.sort.sortChange
    ];

    return merge(...dataMutations).pipe(map(() => {
      return this.getPagedData(this.getSortedData([...this.data]));
    }));
  }

  /**
   *  Called when the table is being destroyed. Use this function, to clean up
   * any open connections or free any held resources that were set up during connect.
   */
  // tslint:disable-next-line:typedef
  disconnect() {
  }

  /**
   * Paginate the data (client-side). If you're using server-side pagination,
   * this would be replaced by requesting the appropriate data from the server.
   */
  private getPagedData(data: CatalogoItem[]) {
    const startIndex = this.paginator.pageIndex * this.paginator.pageSize;
    return data.splice(startIndex, this.paginator.pageSize);
  }

  /**
   * Sort the data (client-side). If you're using server-side sorting,
   * this would be replaced by requesting the appropriate data from the server.
   */
  private getSortedData(data: CatalogoItem[]) {
    if (!this.sort.active || this.sort.direction === '') {
      return data;
    }

    return data.sort((a, b) => {
      const isAsc = this.sort.direction === 'asc';
      switch (this.sort.active) {
        case 'name':
          return compare(a.descricao, b.descricao, isAsc);
        case 'id':
          return compare(+a.id, +b.id, isAsc);
        default:
          return 0;
      }
    });
  }
}

/** Simple sort comparator for example ID/Name columns (for client-side sorting). */
function compare(a: string | number, b: string | number, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
