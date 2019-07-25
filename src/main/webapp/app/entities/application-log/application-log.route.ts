import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApplicationLog } from 'app/shared/model/application-log.model';
import { ApplicationLogService } from './application-log.service';
import { ApplicationLogComponent } from './application-log.component';
import { ApplicationLogDetailComponent } from './application-log-detail.component';
import { ApplicationLogUpdateComponent } from './application-log-update.component';
import { ApplicationLogDeletePopupComponent } from './application-log-delete-dialog.component';
import { IApplicationLog } from 'app/shared/model/application-log.model';

@Injectable({ providedIn: 'root' })
export class ApplicationLogResolve implements Resolve<IApplicationLog> {
  constructor(private service: ApplicationLogService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IApplicationLog> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ApplicationLog>) => response.ok),
        map((applicationLog: HttpResponse<ApplicationLog>) => applicationLog.body)
      );
    }
    return of(new ApplicationLog());
  }
}

export const applicationLogRoute: Routes = [
  {
    path: '',
    component: ApplicationLogComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.applicationLog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ApplicationLogDetailComponent,
    resolve: {
      applicationLog: ApplicationLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.applicationLog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ApplicationLogUpdateComponent,
    resolve: {
      applicationLog: ApplicationLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.applicationLog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ApplicationLogUpdateComponent,
    resolve: {
      applicationLog: ApplicationLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.applicationLog.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const applicationLogPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ApplicationLogDeletePopupComponent,
    resolve: {
      applicationLog: ApplicationLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.applicationLog.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
