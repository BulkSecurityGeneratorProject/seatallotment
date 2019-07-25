import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SeatPartition } from 'app/shared/model/seat-partition.model';
import { SeatPartitionService } from './seat-partition.service';
import { SeatPartitionComponent } from './seat-partition.component';
import { SeatPartitionDetailComponent } from './seat-partition-detail.component';
import { SeatPartitionUpdateComponent } from './seat-partition-update.component';
import { SeatPartitionDeletePopupComponent } from './seat-partition-delete-dialog.component';
import { ISeatPartition } from 'app/shared/model/seat-partition.model';

@Injectable({ providedIn: 'root' })
export class SeatPartitionResolve implements Resolve<ISeatPartition> {
  constructor(private service: SeatPartitionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISeatPartition> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SeatPartition>) => response.ok),
        map((seatPartition: HttpResponse<SeatPartition>) => seatPartition.body)
      );
    }
    return of(new SeatPartition());
  }
}

export const seatPartitionRoute: Routes = [
  {
    path: '',
    component: SeatPartitionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.seatPartition.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SeatPartitionDetailComponent,
    resolve: {
      seatPartition: SeatPartitionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.seatPartition.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SeatPartitionUpdateComponent,
    resolve: {
      seatPartition: SeatPartitionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.seatPartition.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SeatPartitionUpdateComponent,
    resolve: {
      seatPartition: SeatPartitionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.seatPartition.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const seatPartitionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SeatPartitionDeletePopupComponent,
    resolve: {
      seatPartition: SeatPartitionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.seatPartition.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
