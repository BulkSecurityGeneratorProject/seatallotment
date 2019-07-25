/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SeatAllotmentTestModule } from '../../../test.module';
import { ApplicationlogComponent } from 'app/entities/applicationlog/applicationlog.component';
import { ApplicationlogService } from 'app/entities/applicationlog/applicationlog.service';
import { Applicationlog } from 'app/shared/model/applicationlog.model';

describe('Component Tests', () => {
  describe('Applicationlog Management Component', () => {
    let comp: ApplicationlogComponent;
    let fixture: ComponentFixture<ApplicationlogComponent>;
    let service: ApplicationlogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SeatAllotmentTestModule],
        declarations: [ApplicationlogComponent],
        providers: []
      })
        .overrideTemplate(ApplicationlogComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApplicationlogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicationlogService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Applicationlog('9fec3727-3421-4967-b213-ba36557ca194')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.applicationlogs[0]).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
  });
});
