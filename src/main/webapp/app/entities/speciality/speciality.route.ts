import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Speciality } from 'app/shared/model/speciality.model';
import { SpecialityService } from './speciality.service';
import { SpecialityComponent } from './speciality.component';
import { SpecialityDetailComponent } from './speciality-detail.component';
import { SpecialityUpdateComponent } from './speciality-update.component';
import { SpecialityDeletePopupComponent } from './speciality-delete-dialog.component';
import { ISpeciality } from 'app/shared/model/speciality.model';

@Injectable({ providedIn: 'root' })
export class SpecialityResolve implements Resolve<ISpeciality> {
  constructor(private service: SpecialityService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISpeciality> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Speciality>) => response.ok),
        map((speciality: HttpResponse<Speciality>) => speciality.body)
      );
    }
    return of(new Speciality());
  }
}

export const specialityRoute: Routes = [
  {
    path: '',
    component: SpecialityComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.speciality.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SpecialityDetailComponent,
    resolve: {
      speciality: SpecialityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.speciality.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SpecialityUpdateComponent,
    resolve: {
      speciality: SpecialityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.speciality.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SpecialityUpdateComponent,
    resolve: {
      speciality: SpecialityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.speciality.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const specialityPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SpecialityDeletePopupComponent,
    resolve: {
      speciality: SpecialityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'seatAllotmentApp.speciality.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
