package com.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.seatrend.routinginspection.db.table.PlanTable;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PLAN_TABLE".
*/
public class PlanTableDao extends AbstractDao<PlanTable, Long> {

    public static final String TABLENAME = "PLAN_TABLE";

    /**
     * Properties of entity PlanTable.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Lsh = new Property(1, String.class, "lsh", false, "LSH");
        public final static Property Xzlx = new Property(2, String.class, "xzlx", false, "XZLX");
        public final static Property Jhzt = new Property(3, String.class, "jhzt", false, "JHZT");
        public final static Property Xzrq = new Property(4, Long.class, "xzrq", false, "XZRQ");
        public final static Property ROW_ID = new Property(5, int.class, "ROW_ID", false, "ROW__ID");
        public final static Property Bmmc = new Property(6, String.class, "bmmc", false, "BMMC");
        public final static Property Glbm = new Property(7, String.class, "glbm", false, "GLBM");
        public final static Property Userdh = new Property(8, String.class, "userdh", false, "USERDH");
        public final static Property Jhbz = new Property(9, String.class, "jhbz", false, "JHBZ");
    }

    private DaoSession daoSession;


    public PlanTableDao(DaoConfig config) {
        super(config);
    }
    
    public PlanTableDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PLAN_TABLE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"LSH\" TEXT," + // 1: lsh
                "\"XZLX\" TEXT," + // 2: xzlx
                "\"JHZT\" TEXT," + // 3: jhzt
                "\"XZRQ\" INTEGER," + // 4: xzrq
                "\"ROW__ID\" INTEGER NOT NULL ," + // 5: ROW_ID
                "\"BMMC\" TEXT," + // 6: bmmc
                "\"GLBM\" TEXT," + // 7: glbm
                "\"USERDH\" TEXT," + // 8: userdh
                "\"JHBZ\" TEXT);"); // 9: jhbz
        // Add Indexes
        db.execSQL("CREATE UNIQUE INDEX " + constraint + "IDX_PLAN_TABLE_LSH ON \"PLAN_TABLE\"" +
                " (\"LSH\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PLAN_TABLE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PlanTable entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String lsh = entity.getLsh();
        if (lsh != null) {
            stmt.bindString(2, lsh);
        }
 
        String xzlx = entity.getXzlx();
        if (xzlx != null) {
            stmt.bindString(3, xzlx);
        }
 
        String jhzt = entity.getJhzt();
        if (jhzt != null) {
            stmt.bindString(4, jhzt);
        }
 
        Long xzrq = entity.getXzrq();
        if (xzrq != null) {
            stmt.bindLong(5, xzrq);
        }
        stmt.bindLong(6, entity.getROW_ID());
 
        String bmmc = entity.getBmmc();
        if (bmmc != null) {
            stmt.bindString(7, bmmc);
        }
 
        String glbm = entity.getGlbm();
        if (glbm != null) {
            stmt.bindString(8, glbm);
        }
 
        String userdh = entity.getUserdh();
        if (userdh != null) {
            stmt.bindString(9, userdh);
        }
 
        String jhbz = entity.getJhbz();
        if (jhbz != null) {
            stmt.bindString(10, jhbz);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PlanTable entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String lsh = entity.getLsh();
        if (lsh != null) {
            stmt.bindString(2, lsh);
        }
 
        String xzlx = entity.getXzlx();
        if (xzlx != null) {
            stmt.bindString(3, xzlx);
        }
 
        String jhzt = entity.getJhzt();
        if (jhzt != null) {
            stmt.bindString(4, jhzt);
        }
 
        Long xzrq = entity.getXzrq();
        if (xzrq != null) {
            stmt.bindLong(5, xzrq);
        }
        stmt.bindLong(6, entity.getROW_ID());
 
        String bmmc = entity.getBmmc();
        if (bmmc != null) {
            stmt.bindString(7, bmmc);
        }
 
        String glbm = entity.getGlbm();
        if (glbm != null) {
            stmt.bindString(8, glbm);
        }
 
        String userdh = entity.getUserdh();
        if (userdh != null) {
            stmt.bindString(9, userdh);
        }
 
        String jhbz = entity.getJhbz();
        if (jhbz != null) {
            stmt.bindString(10, jhbz);
        }
    }

    @Override
    protected final void attachEntity(PlanTable entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public PlanTable readEntity(Cursor cursor, int offset) {
        PlanTable entity = new PlanTable( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // lsh
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // xzlx
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // jhzt
            cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4), // xzrq
            cursor.getInt(offset + 5), // ROW_ID
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // bmmc
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // glbm
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // userdh
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // jhbz
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, PlanTable entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setLsh(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setXzlx(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setJhzt(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setXzrq(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
        entity.setROW_ID(cursor.getInt(offset + 5));
        entity.setBmmc(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setGlbm(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setUserdh(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setJhbz(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(PlanTable entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(PlanTable entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(PlanTable entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}