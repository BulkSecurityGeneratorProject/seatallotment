import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Applicationlog } from 'app/shared/model/applicationlog.model';
import { ApplicationlogService } from './applicationlog.service';
import { ApplicationlogComponent } from './applicationlog.component';
import { ApplicationlogDetailComponent } from './applicationlog-detail.component';
import { ApplicationlogUpdateComponent } from './applicationlog-update.component';
import { ApplicationlogDeletePopupComponent } from './applicationlog-delete-dialog.component';
import { IApplicationlog } from 'app/shared/model/applicationlog.model';

@Injectable({ providedIn: 'root' })
export class ApplicationlogResolve implements Resolve<IApplicationlog> {
  constructor(private service: ApplicationlogService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IApplicationlog> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Applicationlog>) => response.ok),
        map((applicationlog: HttpResponse<Applicationlog>) => applicationlog.body)
      );
    }
    return of(new Applicationlog());
  }
}

export const applicationlogRoute: Routes = [
  {
    path: '',
    component: ApplicationlogComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.applicationlog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ApplicationlogDetailComponent,
    resolve: {
      applicationlog: ApplicationlogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.applicationlog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ApplicationlogUpdateComponent,
    resolve: {
      applicationlog: ApplicationlogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.applicationlog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ApplicationlogUpdateComponent,
    resolve: {
      applicationlog: ApplicationlogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.applicationlog.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const applicationlogPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ApplicationlogDeletePopupComponent,
    resolve: {
      applicationlog: ApplicationlogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.applicationlog.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
