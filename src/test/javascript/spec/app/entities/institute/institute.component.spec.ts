/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SeatAllotmentTestModule } from '../../../test.module';
import { InstituteComponent } from 'app/entities/institute/institute.component';
import { InstituteService } from 'app/entities/institute/institute.service';
import { Institute } from 'app/shared/model/institute.model';

describe('Component Tests', () => {
  describe('Institute Management Component', () => {
    let comp: InstituteComponent;
    let fixture: ComponentFixture<InstituteComponent>;
    let service: InstituteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SeatAllotmentTestModule],
        declarations: [InstituteComponent],
        providers: []
      })
        .overrideTemplate(InstituteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InstituteComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InstituteService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Institute('9fec3727-3421-4967-b213-ba36557ca194')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.institutes[0]).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
  });
});
