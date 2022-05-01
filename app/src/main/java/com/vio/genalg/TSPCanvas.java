package com.vio.genalg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.vio.genalg.genalg.TSPChromosome;

import java.util.ArrayList;

public class TSPCanvas extends View {

    private boolean end;

    private Paint paint;

    private Context context;

    public ArrayList<Point> points;

    private boolean readonly;

    private TSPChromosome chromosome;
    private TSPChromosome best;

    public TSPCanvas(Context context) {
        super(context);
        readonly = false;
        this.context = context;
        paint = new Paint();
        paint.setColor(Color.parseColor("#227093"));
        paint.setTextSize(32);
        points = new ArrayList<>();
        end = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStrokeWidth(3f);
        paint.setColor(Color.parseColor("#AAAAAA"));
        if (chromosome != null) {
            for (int i = 0; i < chromosome.getGenes().size() - 1; i++) {
                Point start = points.get(chromosome.getGenes().get(i));
                Point end = points.get(chromosome.getGenes().get(i + 1));

                canvas.drawLine(start.getX(), start.getY(), end.getX(), end.getY(), paint);
            }

            Point start = points.get(chromosome.getGenes().get(0));
            Point end = points.get(chromosome.getGenes().get(chromosome.getGenes().size() - 1));

            canvas.drawLine(start.getX(), start.getY(), end.getX(), end.getY(), paint);
        }

        paint.setStrokeWidth(6f);
        paint.setColor(Color.parseColor("#FF0000"));
        if (best != null) {
            for (int i = 0; i < best.getGenes().size() - 1; i++) {
                Point start = points.get(best.getGenes().get(i));
                Point end = points.get(best.getGenes().get(i + 1));

                canvas.drawLine(start.getX(), start.getY(), end.getX(), end.getY(), paint);
            }


            Point start = points.get(best.getGenes().get(0));
            Point end = points.get(best.getGenes().get(best.getGenes().size() - 1));

            canvas.drawLine(start.getX(), start.getY(), end.getX(), end.getY(), paint);
        }

        paint.setColor(Color.parseColor("#227093"));
        for (int i = 0; i < points.size(); i++) {
            if (end) {
                paint.setColor(Color.parseColor("#FF0000"));
            }
            canvas.drawCircle(points.get(i).getX(), points.get(i).getY(), 16, paint);
        }

        paint.setColor(Color.parseColor("#000000"));
        for (int i = 0; i < points.size(); i++) {
            canvas.drawText("City " + (i+1), points.get(i).getX() - 32, points.get(i).getY() - 32, paint);
        }

        //System.out.println("-------------------------");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (readonly) {
            return true;
        }

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                points.add(new Point(x, y));
                invalidate();
                break;
        }
        return true;
    }

    public void clearPoints() {
        this.points.clear();
        invalidate();
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public void setChromosome(TSPChromosome chromosome) {
        this.chromosome = chromosome;
    }
    public void setBest(TSPChromosome chromosome) {
        this.best = chromosome;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }
}
