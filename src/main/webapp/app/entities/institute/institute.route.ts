import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Institute } from 'app/shared/model/institute.model';
import { InstituteService } from './institute.service';
import { InstituteComponent } from './institute.component';
import { InstituteDetailComponent } from './institute-detail.component';
import { InstituteUpdateComponent } from './institute-update.component';
import { InstituteDeletePopupComponent } from './institute-delete-dialog.component';
import { IInstitute } from 'app/shared/model/institute.model';

@Injectable({ providedIn: 'root' })
export class InstituteResolve implements Resolve<IInstitute> {
  constructor(private service: InstituteService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInstitute> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Institute>) => response.ok),
        map((institute: HttpResponse<Institute>) => institute.body)
      );
    }
    return of(new Institute());
  }
}

export const instituteRoute: Routes = [
  {
    path: '',
    component: InstituteComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.institute.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InstituteDetailComponent,
    resolve: {
      institute: InstituteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.institute.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InstituteUpdateComponent,
    resolve: {
      institute: InstituteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.institute.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InstituteUpdateComponent,
    resolve: {
      institute: InstituteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.institute.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const institutePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InstituteDeletePopupComponent,
    resolve: {
      institute: InstituteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.institute.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
