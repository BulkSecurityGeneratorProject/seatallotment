/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SeatAllotmentTestModule } from '../../../test.module';
import { ApplicationLogComponent } from 'app/entities/application-log/application-log.component';
import { ApplicationLogService } from 'app/entities/application-log/application-log.service';
import { ApplicationLog } from 'app/shared/model/application-log.model';

describe('Component Tests', () => {
  describe('ApplicationLog Management Component', () => {
    let comp: ApplicationLogComponent;
    let fixture: ComponentFixture<ApplicationLogComponent>;
    let service: ApplicationLogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SeatAllotmentTestModule],
        declarations: [ApplicationLogComponent],
        providers: []
      })
        .overrideTemplate(ApplicationLogComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApplicationLogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicationLogService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ApplicationLog('9fec3727-3421-4967-b213-ba36557ca194')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.applicationLogs[0]).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
  });
});
