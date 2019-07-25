/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SeatAllotmentTestModule } from '../../../test.module';
import { SpecialityComponent } from 'app/entities/speciality/speciality.component';
import { SpecialityService } from 'app/entities/speciality/speciality.service';
import { Speciality } from 'app/shared/model/speciality.model';

describe('Component Tests', () => {
  describe('Speciality Management Component', () => {
    let comp: SpecialityComponent;
    let fixture: ComponentFixture<SpecialityComponent>;
    let service: SpecialityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SeatAllotmentTestModule],
        declarations: [SpecialityComponent],
        providers: []
      })
        .overrideTemplate(SpecialityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpecialityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpecialityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Speciality('9fec3727-3421-4967-b213-ba36557ca194')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.specialities[0]).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
  });
});
