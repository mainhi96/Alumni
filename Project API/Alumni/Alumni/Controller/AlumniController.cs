using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Alumni.Controller
{
    public class AlumniController : ApiController
    {
        [HttpGet]
        public List<account> GetAccList()
        {
            DBAlumniDataContext db = new DBAlumniDataContext();
            return db.accounts.ToList();

        }
        [HttpGet]
        public List<info> GetInfoList(char a) //de phan biet 2 ham getinfo va get acc
        {
            DBAlumniDataContext db = new DBAlumniDataContext();
            return db.infos.ToList();

        }
        [HttpGet]
        public account GetAcc(string nameacc)
        {
            DBAlumniDataContext db = new DBAlumniDataContext();
            return db.accounts.FirstOrDefault(x => x.user == nameacc);
        }
        [HttpGet]
        public info GetInfo(string nameinfo)
        {
            DBAlumniDataContext db = new DBAlumniDataContext();
            return db.infos.FirstOrDefault(x => x.username == nameinfo);
        }
       
        [HttpPost]
        public bool InsertAcc(string name, string pass)
        {
            try
            {
                DBAlumniDataContext db = new DBAlumniDataContext();
                account acc = new account();
                acc.user = name;
                acc.passwords = pass;
                db.accounts.InsertOnSubmit(acc);
                db.SubmitChanges();
                return true;
            }
            catch
            {
                return false;
            }
        }
        [HttpPost]
        public bool InsertInfo(string name, string job, int start, int end)
        {
            try
            {
                DBAlumniDataContext db = new DBAlumniDataContext();
                if (db.accounts.FirstOrDefault(x => x.user == name) == null) return false;
                info inf = new info();
                inf.username = name;
                inf.job = job;
                inf.startyear = start;
                inf.endyear = end;
                db.infos.InsertOnSubmit(inf);
                db.SubmitChanges();
                return true;
            }
            catch
            {
                return false;
            }

        }
        [HttpPut]
        public bool UpdateAcc(string user, string pass)
        {
            {
                try
                {
                    DBAlumniDataContext db = new DBAlumniDataContext();
                    //lấy food tồn tại ra
                    account acc = db.accounts.FirstOrDefault(x => x.user == user);
                    if (acc == null) return false;//không tồn tại false
                    acc.passwords = pass;
                    db.SubmitChanges();//xác nhận chỉnh sửa
                    return true;
                }
                catch
                {
                    return false;
                }
            }
        }
        [HttpPut]
        public bool UpdateInfo(string user, string job, int start, int end)
        {
            {
                try
                {
                    DBAlumniDataContext db = new DBAlumniDataContext();
                    info inf = db.infos.FirstOrDefault(x => x.username == user);
                    if (inf == null) return false;
                    inf.job = job;
                    inf.startyear = start;
                    inf.endyear = end;
                   
                    db.SubmitChanges();//xác nhận chỉnh sửa
                    return true;
                }
                catch
                {
                    return false;
                }
            }
        }
        [HttpDelete]
        public bool DeleteInfo(string usernamedelinfo)
        {
            DBAlumniDataContext db = new DBAlumniDataContext();
            info inf = db.infos.FirstOrDefault(x => x.username == usernamedelinfo);
            if (inf == null) return false;
            db.infos.DeleteOnSubmit(inf);
            db.SubmitChanges();
            return true;
        }
        [HttpDelete]
        public bool DeleteAcc(string usernamedelacc)
        {
            DBAlumniDataContext db = new DBAlumniDataContext();
            account acc = db.accounts.FirstOrDefault(x => x.user == usernamedelacc); 
            if (acc == null) return false;
            db.accounts.DeleteOnSubmit(acc);
            DeleteInfo(usernamedelacc);
            db.SubmitChanges();
            return true;
        }
    }
}
