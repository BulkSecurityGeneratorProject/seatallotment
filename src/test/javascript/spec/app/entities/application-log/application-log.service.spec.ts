/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ApplicationLogService } from 'app/entities/application-log/application-log.service';
import { IApplicationLog, ApplicationLog } from 'app/shared/model/application-log.model';

describe('Service Tests', () => {
  describe('ApplicationLog Service', () => {
    let injector: TestBed;
    let service: ApplicationLogService;
    let httpMock: HttpTestingController;
    let elemDefault: IApplicationLog;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ApplicationLogService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ApplicationLog('ID', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            action_date: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find('9fec3727-3421-4967-b213-ba36557ca194')
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a ApplicationLog', async () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            action_date: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            action_date: currentDate
          },
          returnedFromService
        );
        service
          .create(new ApplicationLog(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a ApplicationLog', async () => {
        const returnedFromService = Object.assign(
          {
            application_no: 'BBBBBB',
            action: 'BBBBBB',
            action_by: 'BBBBBB',
            action_date: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            action_date: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of ApplicationLog', async () => {
        const returnedFromService = Object.assign(
          {
            application_no: 'BBBBBB',
            action: 'BBBBBB',
            action_by: 'BBBBBB',
            action_date: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            action_date: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ApplicationLog', async () => {
        const rxPromise = service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
